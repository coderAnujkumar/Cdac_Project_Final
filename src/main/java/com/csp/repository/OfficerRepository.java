package com.csp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.Officer;

/**
 * OfficerRepository
 * ------------------
 * Repository interface for Officer entity.
 * Used for approval and rejection of applications.
 */
public interface OfficerRepository extends JpaRepository<Officer, Integer> {
	
	Optional<Officer> findByEmail(String email);
}
