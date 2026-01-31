package com.csp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.csp.service.CertificateService;

@Controller //Marks this class as a Spring MVC controller
@RequestMapping("/certificate") //Base URL for this controller
public class CertificateDownloadController {

    private final CertificateService certificateService;

    // constructor Injection (REQUIRED because final)
    public CertificateDownloadController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/download/{appId}")
    //This is best practice for file downloads.
    public ResponseEntity<InputStreamResource> downloadCertificate(@PathVariable int appId) throws IOException { //Takes appId from URL

        // Get file path from service
        String path = certificateService.generateCertificate(appId);  //return file path

        File certificate = new File(path); //load file path 

        //read file in read mode show in browser
        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(certificate)); 

        return ResponseEntity.ok() //200 ok
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Certificate_" + appId + ".pdf") //forcefully downloade without this file open in browser
                .contentType(MediaType.APPLICATION_PDF) //tell type of file this is pdf
                .contentLength(certificate.length())
                .body(resource);
    }

}
