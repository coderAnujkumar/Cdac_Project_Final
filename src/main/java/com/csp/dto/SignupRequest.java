package com.csp.dto;

import jakarta.validation.constraints.*;

public class SignupRequest {

	//These are used to validate user input automatically.
    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Enter valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    private String role;

    
    // Constructor
	public SignupRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SignupRequest(@NotBlank(message = "Name is required") String name,
			@Email(message = "Enter valid email") @NotBlank(message = "Email is required") String email,
			@Size(min = 6, message = "Password must be at least 6 characters") String password, String role) {
		super();
		this.name = name;
		this.email = email;
		this.password = password;
		this.role = role;
	}
	
	
	 // getters & setters

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
