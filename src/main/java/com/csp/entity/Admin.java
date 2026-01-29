package com.csp.entity;



import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.*;

/**
 * Admin Entity
 * -------------
 * Represents the ADMIN table in the database.
 * Stores details of system administrators responsible
 * for monitoring the system, managing users, and viewing reports.
 */
@Entity
@Table(name = "admin")
public class Admin {

    /**
     * Primary key of the admin table.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private int adminId;

    /**
     * Username used by admin for login.
     * Must be unique and non-null.
     */
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    /**
     * Encrypted password for admin authentication.
     */
    @Column(name = "password", nullable = false, length = 255)
    private String password;

    /**
     * Role assigned to admin.
     * Example: ROLE_ADMIN
     */
    @Column(name = "role", nullable = false)
    private String role;

    // Default constructor required by JPA
    public Admin() {
    }

    // Parameterized constructor
    public Admin(int adminId, String username, String password, String role) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
    
    
}
    
   

