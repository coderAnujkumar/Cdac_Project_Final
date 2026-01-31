package com.csp.controller;

import java.security.Principal;//Used to get currently logged-in user details (email/username).
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; //send data from controller → Thymeleaf HTML pages.
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.csp.entity.Application;
import com.csp.entity.Citizen;
import com.csp.repository.ApplicationStatusHistoryRepository;
import com.csp.service.ApplicationService;
import com.csp.service.CitizenService;
import com.csp.service.DocumentService;

@Controller //Marks this class as Spring MVC Controller.
@RequestMapping("/citizen") // commone for all url first citizen come and then any api link 
public class CitizenController {

    @Autowired
    private CitizenService citizenService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private DocumentService documentService;

    @Autowired
    private ApplicationStatusHistoryRepository historyRepo; //interface

    // ================= DASHBOARD =================
    @GetMapping("/dashboard")
    public String dashboard(Model model, Principal principal) {

        String email = principal.getName();

        Citizen citizen = citizenService.getCitizenByEmail(email); //Fetch citizen

        List<Application> applications =
                applicationService.getApplicationsByCitizen(citizen.getCitizenId());//Fetch application

        model.addAttribute("citizen", citizen);  //Send Data to UI
        model.addAttribute("applications", applications); //Send Data to UI

        return "citizen-dashboard"; //use this html page 
    } 

    // ================= APPLY PAGE =================
    @GetMapping("/apply")
    public String applyPage() {
        return "citizen-apply";
    }

    // ================= SUBMIT APPLICATION =================
    @PostMapping(value = "/apply", consumes = "multipart/form-data") //handle form Supports file upload
    
    //Receives data from form fields
    public String applyService(@RequestParam String serviceType,
                               @RequestParam String purpose,
                               @RequestParam String address,
                               @RequestParam String documentType,
                               @RequestParam MultipartFile file,
                               Principal principal,
                               RedirectAttributes ra) {

        String email = principal.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);

        try {
        	//Creates new application record
            Application application =
                    applicationService.submitApplication(citizen, serviceType, purpose, address);

            documentService.uploadDocument( //Uploads document and links it to application
                    application.getApplicationId(),
                    documentType,
                    file
            );
            //Message shown after redirect
            ra.addFlashAttribute("success", "Application submitted successfully!");

        } catch (RuntimeException ex) {
            ra.addFlashAttribute("error", ex.getMessage());
        }

        return "redirect:/citizen/dashboard";
    }

    

    // ================= APPLICATION DETAILS =================
    @GetMapping("/application/{id}")
    public String viewApplicationDetails(@PathVariable int id,
                                         Model model,
                                         Principal principal) {

        String email = principal.getName();
        Citizen citizen = citizenService.getCitizenByEmail(email);

        Application app = applicationService.getApplicationById(id);
        //Prevents one citizen viewing another citizen’s application
        if (app.getCitizen().getCitizenId() != citizen.getCitizenId()) {
            throw new RuntimeException("Unauthorized access");
        }
        
//Shows status changes (Submitted → Approved → Rejected etc.)
        model.addAttribute("history",
        		//interface
                historyRepo.findByApplication_ApplicationIdOrderByChangedAtDesc(id));

        model.addAttribute("app", app);

        return "citizen-application-details";
    }

}
