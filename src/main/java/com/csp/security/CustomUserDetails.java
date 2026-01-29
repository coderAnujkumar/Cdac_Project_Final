package com.csp.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * CustomUserDetails
 * ------------------
 * This class represents an authenticated user in the system.
 * Spring Security internally uses this object after successful login.
 *
 * It contains:
 * - Username
 * - Encrypted password
 * - Role (authority)
 */
public class CustomUserDetails implements UserDetails {

    private String username;  // login identifier (email/username)
    private String password;  // encrypted password
    private String role;      // user role (ROLE_CITIZEN, ROLE_ADMIN, etc.)

    public CustomUserDetails(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Defines what authorities (roles) the user has.
     * Spring Security uses this for authorization checks.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Convert role string into GrantedAuthority object
        return List.of(new SimpleGrantedAuthority(role));
    }

    // Returns encrypted password
    @Override
    public String getPassword() {
        return password;
    }

    // Returns username used for login
    @Override
    public String getUsername() {
        return username;
    }

    // Below methods define account status
    // We return true to keep account always active

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
