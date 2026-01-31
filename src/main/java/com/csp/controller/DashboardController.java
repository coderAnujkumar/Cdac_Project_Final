package com.csp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// Redirects logged-in users to their respective dashboards
@Controller
public class DashboardController {

    
    @GetMapping("/dashboard")
    public String redirectDashboard(Authentication authentication) {

        // Fetch logged-in user's role
    	//Provided by Spring Security
        String role = authentication
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority();

        if (role.equals("ROLE_ADMIN"))
            return "redirect:/admin/dashboard";

        if (role.equals("ROLE_OFFICER"))
            return "redirect:/officer/dashboard";

        if (role.equals("ROLE_CLERK"))
            return "redirect:/clerk/dashboard";

        // Default case: Citizen
        return "redirect:/citizen/dashboard";
    }
}
