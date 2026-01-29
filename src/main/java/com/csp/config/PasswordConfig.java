package com.csp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * PasswordConfig
 * ----------------
 * This configuration class defines how passwords
 * are encrypted before being stored in the database.
 *
 * Spring Security uses this encoder during:
 * 1. User registration (password encoding)
 * 2. User login (password verification)
 */
@Configuration   // Marks this as a Spring configuration class
public class PasswordConfig {

    /**
     * PasswordEncoder Bean
     * ---------------------
     * BCryptPasswordEncoder is an industry-standard
     * hashing algorithm that provides:
     * - Strong encryption
     * - Protection against brute-force attacks
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    
}
