package com.csp.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.csp.entity.PasswordResetToken;
import com.csp.entity.User;
import com.csp.repository.PasswordResetTokenRepository;
import com.csp.service.UserService;

@Controller
public class ForgotPasswordController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ---------------- FORGOT PASSWORD PAGE ----------------
    @GetMapping("/forgot-password")
    public String forgotPasswordPage() {
        return "forgot-password";
    }

    // ---------------- SEND RESET LINK ----------------
    @PostMapping("/forgot-password")
    public String sendResetLink(@RequestParam String email, Model model) {

        User user = userService.getUserByUsername(email);

        if (user == null) {
            model.addAttribute("error", "Email not registered");
            return "forgot-password";
        }

        String token = UUID.randomUUID().toString();

        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUser(user);
        resetToken.setExpiryTime(LocalDateTime.now().plusMinutes(15));

        tokenRepository.save(resetToken);

        // Later you will send this via email
        System.out.println("RESET LINK 👉 http://localhost:5556/reset-password?token=" + token);

        model.addAttribute("success", "Password reset link sent to your email");
        return "forgot-password";
    }

    // ---------------- RESET PASSWORD PAGE ----------------
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {

        PasswordResetToken resetToken =
                tokenRepository.findByToken(token).orElse(null);

        if (resetToken == null ||
            resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token");
            return "login";
        }

        model.addAttribute("token", token);
        return "reset-password";
    }

    // ---------------- SAVE NEW PASSWORD ----------------
    @PostMapping("/reset-password")
    public String resetPassword(@RequestParam String token,
                                @RequestParam String password,
                                Model model) {

        PasswordResetToken resetToken =
                tokenRepository.findByToken(token).orElse(null);

        if (resetToken == null ||
            resetToken.getExpiryTime().isBefore(LocalDateTime.now())) {
            model.addAttribute("error", "Invalid or expired token");
            return "login";
        }

        User user = resetToken.getUser();
        user.setPassword(passwordEncoder.encode(password));
        userService.saveUser(user);

        tokenRepository.delete(resetToken);

        model.addAttribute("success", "Password reset successful. Please login.");
        return "login";
    }
}
