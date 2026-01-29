package com.csp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.csp.entity.Document;

/**
 * DocumentRepository
 * -------------------
 * Repository interface for Document entity.
 * Handles document retrieval for verification.
 */
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    /**
     * Fetch all documents linked to a specific application.
     *
     * @param applicationId application identifier
     * @return list of documents
     */
	@Query("SELECT d.documentName FROM Document d WHERE d.application.applicationId = :appId")
	List<String> findDocumentNamesByApplicationId(@Param("appId") int appId);
	
	List<Document> findByApplicationApplicationId(int applicationId);

}
