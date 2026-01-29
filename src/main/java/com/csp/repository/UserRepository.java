package com.csp.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.User;

/**
 * UserRepository
 * ----------------
 * Used by Spring Security to fetch user credentials.
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Find user by username
     */
    Optional<User> findByUsername(String username);

    /**
     * Check if user exists
     */
    boolean existsByUsername(String username);
}
