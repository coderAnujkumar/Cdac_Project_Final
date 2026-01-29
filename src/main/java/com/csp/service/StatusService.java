package com.csp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.csp.entity.Status;
import com.csp.repository.StatusRepository;

/**
 * StatusService
 * --------------
 * Handles all business logic related to system status values.
 * Status is treated as a master entity to maintain consistency
 * across Citizen, Clerk, Officer, and Application modules.
 */
@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    /**
     * Fetch a Status entity using its name.
     * Example values: ACTIVE, PENDING, APPROVED, REJECTED
     *
     * @param statusName name of the status
     * @return Status entity
     */
    public Status getStatusByName(String statusName) {
        return statusRepository.findByStatusName(statusName);
    }
}
