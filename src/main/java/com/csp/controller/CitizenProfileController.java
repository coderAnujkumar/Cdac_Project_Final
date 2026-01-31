package com.csp.controller;

import java.nio.file.Files;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csp.entity.Application;
import com.csp.entity.Citizen;
import com.csp.service.ApplicationService;
import com.csp.service.CitizenService;


//citizen profile features:

@Controller //Marks this class as a Spring MVC controller
@RequestMapping("/citizen/profile")
public class CitizenProfileController {

    private static final String UPLOAD_DIR = "uploads/profile/"; //Location where profile photos are stored

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ApplicationService applicationService;

    // ================= PROFILE PAGE =================
    @GetMapping
    public String profilePage(Model model, Principal principal) {

        String email = principal.getName();

        Citizen citizen = citizenService.getCitizenByEmail(email);

        List<Application> applications =//Fetch Applications
                applicationService.getApplicationsByCitizen(citizen.getCitizenId());

        model.addAttribute("citizen", citizen);
        model.addAttribute("applications", applications); // for status if needed

        return "profile";
    }

    // ================= UPDATE NAME + PHOTO =================
    @PostMapping("/update")
    public String updateProfile(@RequestParam String name,
                                @RequestParam(required = false) MultipartFile photo,//photo is optional
                                Principal principal,
                                RedirectAttributes ra) {

        String fileName = null;

        try {
            if (photo != null && !photo.isEmpty()) {

                //check file if not present create folder
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {//Prevents FileNotFoundException
                    Files.createDirectories(uploadPath);
                }

                //for unique file name to avoid conflicts
                fileName = UUID.randomUUID() + "_" + photo.getOriginalFilename();
                
                //copy uploaded file to server and replace existing
                Files.copy(photo.getInputStream(),
                        uploadPath.resolve(fileName),
                        StandardCopyOption.REPLACE_EXISTING);
            }

            citizenService.updateCitizenProfile(principal.getName(), name, fileName); //update to database

          //send data from one request to the next request after a redirect.
            ra.addFlashAttribute("success", "Profile updated successfully");

        } catch (Exception e) {
            e.printStackTrace();
            ra.addFlashAttribute("error", "Profile update failed");
        }

        return "redirect:/citizen/profile"; //redirect to same page profile page  
    }

    // ================= CHANGE PASSWORD =================
    @PostMapping("/change-password")
    public String changePassword(@RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 Principal principal,
                                 RedirectAttributes ra) {

        boolean ok = citizenService.changePassword(
                principal.getName(), oldPassword, newPassword); //verified ald password Encrypts new password Updates DB

        if (ok)
        	//send data from one request to the next request after a redirect.
            ra.addFlashAttribute("success", "Password changed successfully");
        else
            ra.addFlashAttribute("error", "Old password is incorrect");

        return "redirect:/citizen/profile";
    }
}
