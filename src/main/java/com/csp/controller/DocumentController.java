package com.csp.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.csp.entity.Document;
import com.csp.repository.DocumentRepository;

@RestController
@RequestMapping("/documents")
public class DocumentController {

    // ✅ SAME FOLDER AS UPLOAD
    private static final String UPLOAD_DIR =
            "/Users/anujkumar/Desktop/Cdac Project/uploads/documents/";

    private final DocumentRepository documentRepository;

    public DocumentController(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<ByteArrayResource> viewDocument(@PathVariable int id) throws IOException {

        System.out.println("DOCUMENT VIEW API HIT: " + id);

        Document doc = documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        Path path = Paths.get(UPLOAD_DIR + doc.getFilePath());

        System.out.println("FILE PATH = " + path);

        if (!Files.exists(path)) {
            System.out.println("FILE NOT FOUND");
            return ResponseEntity.notFound().build();
        }

        byte[] data = Files.readAllBytes(path);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + doc.getDocumentName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new ByteArrayResource(data));
    }
}
