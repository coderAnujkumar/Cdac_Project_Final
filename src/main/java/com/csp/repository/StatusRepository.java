package com.csp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.Status;

/**
 * StatusRepository
 * -----------------
 * Repository interface for Status entity.
 * Provides database operations related to system statuses
 * such as ACTIVE, PENDING, APPROVED, REJECTED, etc.
 */
public interface StatusRepository extends JpaRepository<Status, Integer> {

    /**
     * Fetch a Status entity by its name.
     * Example: findByStatusName("ACTIVE")
     *
     * @param statusName name of the status
     * @return Status entity
     */
    Status findByStatusName(String statusName);
}
