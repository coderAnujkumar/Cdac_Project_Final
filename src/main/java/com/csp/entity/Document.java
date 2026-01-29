package com.csp.entity;

import jakarta.persistence.*;

/**
 * Document Entity
 * ----------------
 * This class represents the DOCUMENT table in the database.
 * It stores metadata of documents uploaded by citizens
 * as part of their service applications.
 *
 * Actual document files are stored on the server,
 * while this entity maintains document details and
 * links them to the corresponding application.
 */
@Entity
@Table(name = "document")
public class Document {

    /**
     * Primary key of the document table.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "document_id")
    private int documentId;

    /**
     * Original name of the uploaded document file.
     * Example: aadhaar.pdf, income_proof.jpg
     */
    @Column(name = "document_name", nullable = false)
    private String documentName;

    /**
     * Logical document type.
     * Example: Aadhaar Card, Income Proof, Caste Certificate
     */
    @Column(name = "document_type", nullable = false)
    private String documentType;

    /**
     * Path where the uploaded document file
     * is stored on the server.
     * Example: uploads/1705823456789_aadhaar.pdf
     */
    @Column(name = "file_path", nullable = false)
    private String filePath;

    /**
     * Application to which this document belongs.
     * One application can have multiple documents.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

    /**
     * Default constructor required by JPA.
     */
    public Document() {
    }

    /**
     * Parameterized constructor.
     */
    public Document(int documentId,
                    String documentName,
                    String documentType,
                    String filePath,
                    Application application) {

        this.documentId = documentId;
        this.documentName = documentName;
        this.documentType = documentType;
        this.filePath = filePath;
        this.application = application;
    }

    // -------------------- Getters & Setters --------------------

    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
