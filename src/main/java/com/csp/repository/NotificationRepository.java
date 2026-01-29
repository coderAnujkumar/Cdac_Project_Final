package com.csp.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.csp.entity.Notification;

/**
 * NotificationRepository
 * -----------------------
 * Repository interface for Notification entity.
 * Used to fetch notifications related to applications.
 */
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    /**
     * Fetch notifications for a specific application.
     *
     * @param applicationId application identifier
     * @return list of notifications
     */
    List<Notification> findByApplicationApplicationId(int applicationId);
}
