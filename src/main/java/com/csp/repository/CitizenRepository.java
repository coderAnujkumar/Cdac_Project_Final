package com.csp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.Citizen;

/**
 * CitizenRepository
 * ------------------
 * Repository interface for Citizen entity.
 * Handles citizen registration, login, and profile operations.
 */
public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

    /**
     * Fetch a citizen using email address.
     * Email is used as the login identifier.
     *
     * @param email citizen email
     * @return Optional Citizen
     */
    Optional<Citizen> findByEmail(String email);
}
