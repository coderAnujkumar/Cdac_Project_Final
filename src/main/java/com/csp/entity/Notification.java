package com.csp.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Notification Entity
 * --------------------
 * This class represents the NOTIFICATION table in the database.
 * It is used to store notifications sent to citizens regarding
 * application status updates and system messages.
 *
 * Each notification is linked to a specific application.
 */
@Entity                         // Marks this class as a JPA entity
@Table(name = "notification")   // Maps this entity to the "notification" table
public class Notification {

    /**
     * Primary key of the notification table.
     * Auto-generated using database identity strategy.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private int notificationId;

    /**
     * Message content of the notification.
     * Describes the information sent to the citizen.
     */
    @Column(name = "message", nullable = false, length = 255)
    private String message;

    /**
     * Date on which the notification was sent.
     */
    @Column(name = "sent_date")
    private LocalDate sentDate;

    /**
     * Application associated with this notification.
     * Many notifications can belong to one application.
     * Lazy fetching is used for performance optimization.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id")
    private Application application;

    /**
     * Default constructor required by JPA.
     */
    public Notification() {
    }

    /**
     * Parameterized constructor used to create
     * Notification objects with predefined values.
     *
     * @param notificationId unique identifier of the notification
     * @param message notification message
     * @param sentDate date of notification
     * @param application associated application
     */
    public Notification(int notificationId, String message, LocalDate sentDate, Application application) {
        this.notificationId = notificationId;
        this.message = message;
        this.sentDate = sentDate;
        this.application = application;
    }

    /**
     * Getter for notificationId.
     * @return unique identifier of the notification
     */
    public int getNotificationId() {
        return notificationId;
    }

    /**
     * Setter for notificationId.
     * @param notificationId unique identifier to set
     */
    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    /**
     * Getter for notification message.
     * @return message content
     */
    public String getMessage() {
        return message;
    }

    /**
     * Setter for notification message.
     * @param message message content
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Getter for sent date.
     * @return date the notification was sent
     */
    public LocalDate getSentDate() {
        return sentDate;
    }

    /**
     * Setter for sent date.
     * @param sentDate date of sending notification
     */
    public void setSentDate(LocalDate sentDate) {
        this.sentDate = sentDate;
    }

    /**
     * Getter for associated application.
     * @return application linked to this notification
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Setter for associated application.
     * @param application application to associate
     */
    public void setApplication(Application application) {
        this.application = application;
    }
}
