package com.csp.service;

import org.springframework.stereotype.Service;
import com.csp.entity.*;
import com.csp.repository.ApplicationStatusHistoryRepository;

@Service
public class StatusHistoryService {

    private final ApplicationStatusHistoryRepository historyRepo;

    public StatusHistoryService(ApplicationStatusHistoryRepository historyRepo) {
        this.historyRepo = historyRepo;
    }

    public void logStatusChange(Application app,
                                String oldStatus,
                                String newStatus,
                                String role,
                                String username) {

        ApplicationStatusHistory history =
                new ApplicationStatusHistory(app, oldStatus, newStatus, role, username);

        historyRepo.save(history);
    }
}
