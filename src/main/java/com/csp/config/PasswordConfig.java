package com.csp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration   // Marks this as a Spring configuration class
public class PasswordConfig {

    
    @Bean
    //interface
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //secure implementation
    }
    
    
}


//BCrypt:Is a one-way hashing algorithm
