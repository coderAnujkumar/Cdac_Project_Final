package com.csp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.Admin;

/**
 * AdminRepository
 * ----------------
 * Repository interface for Admin entity.
 * Used for admin authentication and management.
 */
public interface AdminRepository extends JpaRepository<Admin, Integer> {

    /**
     * Fetch admin details using username.
     *
     * @param username admin login username
     * @return Admin entity
     */
   Optional<Admin> findByUsername(String username);
   
  
}
