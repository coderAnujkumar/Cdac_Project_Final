package com.csp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * DashboardController
 * --------------------
 * Redirects logged-in users to their respective dashboards
 * based on assigned roles.
 */
@Controller
public class DashboardController {

    /**
     * After successful login, user is redirected here.
     * Role is checked and user is sent to correct dashboard.
     */
    @GetMapping("/dashboard")
    public String redirectDashboard(Authentication authentication) {

        // Fetch logged-in user's role
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
