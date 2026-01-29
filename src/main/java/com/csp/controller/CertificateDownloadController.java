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

@Controller
@RequestMapping("/certificate")
public class CertificateDownloadController {

    private final CertificateService certificateService;

    // ✅ Constructor Injection (REQUIRED because final)
    public CertificateDownloadController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * 🔽 Download Certificate PDF
     * URL Example: /certificate/download/7
     */
    @GetMapping("/download/{appId}")
    public ResponseEntity<InputStreamResource> downloadCertificate(@PathVariable int appId) throws IOException {

        // 🔹 Get file path from service
        String path = certificateService.generateCertificate(appId);

        File certificate = new File(path);

        InputStreamResource resource =
                new InputStreamResource(new FileInputStream(certificate));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=Certificate_" + appId + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(certificate.length())
                .body(resource);
    }

}
