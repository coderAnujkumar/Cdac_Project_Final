package com.csp.service.impl;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.csp.entity.Application;
import com.csp.entity.Document;
import com.csp.repository.ApplicationRepository;
import com.csp.repository.DocumentRepository;
import com.csp.service.DocumentService;

@Service
public class DocumentServiceImpl implements DocumentService {

    // ✅ REAL FOLDER WHERE FILES ARE STORED
    private static final String UPLOAD_DIR =
            "/Users/anujkumar/Desktop/Cdac Project/uploads/documents/";

    private final DocumentRepository documentRepository;
    private final ApplicationRepository applicationRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               ApplicationRepository applicationRepository) {
        this.documentRepository = documentRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public void uploadDocument(int applicationId, String documentType, MultipartFile file) {

        try {
            if (file == null || file.isEmpty()) {
                throw new RuntimeException("Uploaded file is empty");
            }

            Application application = applicationRepository.findById(applicationId)
                    .orElseThrow(() -> new RuntimeException("Application not found"));

            Path uploadPath = Paths.get(UPLOAD_DIR);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String originalFileName = file.getOriginalFilename();
            String safeFileName = originalFileName.replaceAll("\\s+", "_");
            String uniqueFileName = UUID.randomUUID() + "_" + safeFileName;

            Path filePath = uploadPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Document document = new Document();
            document.setDocumentName(originalFileName);
            document.setDocumentType(documentType);
            document.setFilePath(uniqueFileName); // ONLY FILE NAME
            document.setApplication(application);

            documentRepository.save(document);

        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Document upload failed", ex);
        }
    }

    @Override
    public List<Document> getDocumentsByApplicationId(int applicationId) {
        return documentRepository.findByApplicationApplicationId(applicationId);
    }
}
