package com.csp.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.csp.dto.SignupRequest;
import com.csp.entity.Citizen;
import com.csp.entity.Status;
import com.csp.entity.User;
import com.csp.service.CitizenService;
import com.csp.service.StatusService;
import com.csp.service.UserService;

//this class will handle web pages & requests and show login page show signup page registuer new user 
@Controller
public class AuthController {

    @Autowired  //Dependency Injection. automaticaly create a object of userService 
    private UserService userService;

    @Autowired  //Dependency Injection. automaticaly create a object of CitizenService 
    private CitizenService citizenService;

    @Autowired  //Dependency Injection. automaticaly create a object of StatusService 
    private StatusService statusService;

    @Autowired  //Dependency Injection. automaticaly create a object of PasswordEncoder 
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")   // this api is for open login page anly read 
    public String loginPage() {
        return "login";
    }

    @GetMapping("/signup") //Sends empty object to form with field of SignupRequest
    public String signupPage(Model model) {
        model.addAttribute("signup", new SignupRequest());
        return "signup";
    }

    @PostMapping("/signup")  // handle signup citizien only 
    public String registerUser(
    		// @ModelAttribute form → java object 
    		//@Valid apply validation annotations
            @Valid @ModelAttribute("signup") SignupRequest signup, 
            BindingResult result,  // BindingResult:-contains validation errors
            Model model) {

        if (result.hasErrors()) {  //If form is wrong reload signup page
            return "signup";
        }

        if (userService.userExists(signup.getEmail())) { //Calls Service -> Repository -> DB Prevents duplicate email
            model.addAttribute("error", "Email already registered!");
            return "signup";
        }

        try {
        	// this goes into users table
        	 // used by Spring Security only
            User user = new User();
            user.setUsername(signup.getEmail());
            user.setPassword(passwordEncoder.encode(signup.getPassword()));
            user.setRole("ROLE_CITIZEN"); //only handle one roll citizen;
            user.setEnabled(true);
            userService.saveUser(user);
            Status activeStatus = statusService.getStatusByName("ACTIVE");  //fetch status from master table
            
            //data also save in citizen table 
            Citizen citizen = new Citizen();  //create an object of citizen
            citizen.setName(signup.getName());
            citizen.setEmail(signup.getEmail());
            citizen.setMobile(signup.getMobile());   // ✅ added
            citizen.setPassword(user.getPassword()); // encrypted password
            citizen.setStatus(activeStatus);

            citizenService.registerCitizen(citizen, activeStatus);

        } catch (DataIntegrityViolationException ex) {
            model.addAttribute("error", "Email already exists!");
            return "signup";
        }

        model.addAttribute("success", true);
        return "login";
    }




   
    @GetMapping("/")
    public String home() {
        return "index";
    }
}
