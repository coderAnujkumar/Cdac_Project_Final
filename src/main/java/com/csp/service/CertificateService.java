package com.csp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.repository.ApplicationRepository;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

@Service
public class CertificateService {

    private final ApplicationRepository applicationRepository;

    public CertificateService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    // ✅ NOW accepts appId
    public String generateCertificate(int appId) throws IOException {

        // 🔍 Get application from DB
        Application app = applicationRepository.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        if (app.getCertificatePath() != null) {
            return app.getCertificatePath();  // Already exists
        }
        
        
        

        // 📁 Folder to save certificates
        String folderPath = "certificates/";
        new File(folderPath).mkdirs();

        String filePath = folderPath + "Certificate_" + appId + ".pdf";

        // 🧾 Create PDF
        PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);

        document.add(new Paragraph("GOVERNMENT OF INDIA"));
        document.add(new Paragraph("Citizen Service Portal"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Certificate of Approval"));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("Citizen Name: " + app.getCitizen().getName()));
        document.add(new Paragraph("Application ID: " + app.getApplicationId()));
        document.add(new Paragraph("Service: " + app.getServiceType()));
        document.add(new Paragraph("Status: APPROVED"));

        document.close();

        return new File(filePath).getAbsolutePath();
    }
}
