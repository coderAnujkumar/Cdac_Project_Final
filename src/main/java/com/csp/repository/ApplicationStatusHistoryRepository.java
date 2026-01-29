package com.csp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.ApplicationStatusHistory;
import java.util.List;

public interface ApplicationStatusHistoryRepository
        extends JpaRepository<ApplicationStatusHistory, Integer> {

    List<ApplicationStatusHistory> findByApplication_ApplicationIdOrderByChangedAtDesc(int applicationId);
}
