package com.csp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import com.csp.entity.Admin;
import com.csp.entity.Citizen;
import com.csp.entity.Clerk;
import com.csp.entity.User;
import com.csp.repository.AdminRepository;
import com.csp.repository.CitizenRepository;
import com.csp.repository.ClerkRepository;
import com.csp.repository.OfficerRepository;
import com.csp.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole()) // ROLE_CITIZEN / ROLE_CLERK
                .disabled(!user.isEnabled())
                .build();
    }
}

