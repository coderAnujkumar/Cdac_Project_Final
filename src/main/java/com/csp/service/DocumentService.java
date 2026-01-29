package com.csp.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.csp.entity.Document;

/**
 * DocumentService
 * ----------------
 * Defines operations related to document handling
 * such as uploading and linking documents to applications.
 */
public interface DocumentService {

    /**
     * Uploads a document and associates it with an application.
     *
     * @param applicationId ID of the application
     * @param documentType logical document type
     * @param file uploaded document file
     */
    void uploadDocument(int applicationId, String documentType, MultipartFile file);
    

    List<Document> getDocumentsByApplicationId(int applicationId);
}
