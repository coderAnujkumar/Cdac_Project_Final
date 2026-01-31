package com.csp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.csp.entity.Clerk;
import com.csp.entity.Officer;
import com.csp.service.AdminService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ================= DASHBOARD =================

    @GetMapping("/dashboard")
    public String adminDashboard(Model model) { //Fetches statistics from DB:total applications,approved,rejected
        model.addAttribute("totalApps", adminService.countAllApplications());
        model.addAttribute("approvedApps", adminService.countByStatus("APPROVED"));
        model.addAttribute("rejectedApps", adminService.countByStatus("REJECTED"));
        return "admin-dashboard";
        // it use application table 
    }

    // ================= APPLICATION MONITORING =================

    @GetMapping("/applications") //Fetches all applications Sends list to UI
    public String viewAllApplications(Model model) { //Controller to View Data send krna Model used
        model.addAttribute("applications", adminService.getAllApplications());
        return "admin-applications";
    }

    // ================= USER CREATION =================

    @GetMapping("/create-user")
    public String createUserPage(Model model) {
    	//Thymeleaf form needs an object
        model.addAttribute("clerk", new Clerk());
        model.addAttribute("officer", new Officer());
        return "admin-create-user";
    }

    @PostMapping("/create-clerk")
    //@ModelAttribute Automatically maps form fields → Clerk object
    public String createClerk(@ModelAttribute Clerk clerk) {
        adminService.createClerk(clerk);
        return "redirect:/admin/dashboard";  //redirect o dashboard page 
    }

    @PostMapping("/create-officer")
    public String createOfficer(@ModelAttribute Officer officer) {
        adminService.createOfficer(officer);
        return "redirect:/admin/dashboard";
    }

    // ================= USER MANAGEMENT =================
    
    
    @GetMapping("/citizens")
    public String viewCitizens(Model model) {
        model.addAttribute("citizens", adminService.getAllCitizens());
        return "admin-citizens";
    }


    @GetMapping("/manage-users")
    public String manageUsers(Model model) {
        model.addAttribute("clerks", adminService.getAllClerks());
        model.addAttribute("officers", adminService.getAllOfficers());
        return "admin-manage-users";
    }

    @GetMapping("/clerk/toggle/{id}")  
    public String toggleClerk(@PathVariable int id) {  //pathvariable use in url /id type
        adminService.toggleClerkStatus(id);
        return "redirect:/admin/manage-users";
    }

    @GetMapping("/officer/toggle/{id}")
    public String toggleOfficer(@PathVariable int id) {
        adminService.toggleOfficerStatus(id);
        return "redirect:/admin/manage-users";
    }
}
