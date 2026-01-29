package com.csp.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csp.entity.Application;
import com.csp.entity.Notification;
import com.csp.repository.NotificationRepository;

/**
 * NotificationService
 * --------------------
 * Handles creation and storage of notifications sent to citizens
 * regarding application status updates.
 */
@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    /**
     * Create and save a notification for an application.
     *
     * @param application related application
     * @param message notification message
     */
    public void sendNotification(Application application, String message) {

        Notification notification = new Notification();
        notification.setApplication(application);
        notification.setMessage(message);
        notification.setSentDate(LocalDate.now());

        notificationRepository.save(notification);
    }
}
