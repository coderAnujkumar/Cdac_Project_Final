package com.csp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.Clerk;

/**
 * ClerkRepository
 * ----------------
 * Repository interface for Clerk entity.
 * Used for document verification operations.
 */
public interface ClerkRepository extends JpaRepository<Clerk, Integer> {
	
	Optional<Clerk> findByEmail(String email);
	
}
