package com.csp.entity;

import jakarta.persistence.*;


//This entity is used ONLY for authentication & authorization.
@Entity
@Table(name = "users")
public class User {

    
    @Id //for primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) //autogenrate
    private int id;

   
    @Column(nullable = false, unique = true) //username used for login using email
    private String username;

    
    @Column(nullable = false)
    private String password; // for handle password 

    
    @Column(nullable = false) // for roll handle 
    private String role;

    
    @Column(nullable = false)  
    private boolean enabled = true; // this coloum is use for account enable or disable 

    // Constructors 

    public User() {}

    public User(String username, String password, String role, boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }

    // Getters & Setters 

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
