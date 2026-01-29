package com.csp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csp.entity.User;
import com.csp.repository.UserRepository;
import com.csp.service.UserService;

/**
 * UserServiceImpl
 * ----------------
 * Implements user authentication services.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    /**
     * Save user to database
     */
    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    /**
     * Check if username already exists
     */
    @Override
    public boolean userExists(String username) {
        return userRepository.existsByUsername(username);
    }

    /**
     * Fetch user by username
     */
    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElse(null);
    }
}
