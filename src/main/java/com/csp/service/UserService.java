package com.csp.service;

import com.csp.entity.User;

//only defines what to do, not how.
public interface UserService {

    //Save new user
    void saveUser(User user); 

   
     //Check if username already exists
     
    boolean userExists(String username);

    //Fetch user by username
    User getUserByUsername(String username);
}
