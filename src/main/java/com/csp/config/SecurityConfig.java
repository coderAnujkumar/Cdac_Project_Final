package com.csp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.csp.security.CustomAuthenticationSuccessHandler;
import com.csp.security.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity //method-level security ON karna //@PreAuthorize, @PostAuthorize
//role / permission 

public class SecurityConfig {
//	DEPENDENCY INJECTION/ ya autowired annotation ka use krke bhi same kaam hota hai;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler successHandler;
//constructor injection
    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            CustomAuthenticationSuccessHandler successHandler) {

        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
    }

    // ================= AUTHENTICATION MANAGER ================= WHO ARE YOU?=====
    @Bean // create object 
    //AuthenticationManager is an interface 
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
// Shared Some object that is already in spring 
        builder
            .userDetailsService(userDetailsService) // CustomUserDetailsService etches user from DB
            .passwordEncoder(passwordEncoder);

        return builder.build();
    }

    // ================= SECURITY FILTER CHAIN ============ WHO CAN ACCESS WHAT?=======
    @Bean
    //heart of Spring Security.
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) //Cross-Site Request Forgery
//Spring Security use CSRF Token  REST API (Postman, React, Angular) csrf disable 
            .authorizeHttpRequests(auth -> auth

                // anyone can access these url no need login
                .requestMatchers(
                    "/",
                    "/index",
                    "/home",
                    "/login",
                    "/signup",
                    "/register",
                    "/forgot-password",
                    "/reset-password"
                ).permitAll()

                // check static file 
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // Documents folder Download documents,View uploaded files
                .requestMatchers("/documents/**")
                    .hasAnyRole("CITIZEN", "CLERK", "OFFICER", "ADMIN")

                // ROLE-BASED DASHBOARDS
                .requestMatchers("/citizen/**").hasRole("CITIZEN")
                .requestMatchers("/clerk/**").hasRole("CLERK")
                .requestMatchers("/officer/**").hasRole("OFFICER")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            // 🔑 LOGIN CONFIG
            .formLogin(form -> form
                .loginPage("/login") // open login page
                .successHandler(successHandler)
                .failureUrl("/login?error") // login fail 
                .permitAll()
            )
            .sessionManagement(session -> session
                    .invalidSessionUrl("/login?expired")
                    .maximumSessions(1)        // one login per user
                    .expiredUrl("/login?expired")
                )

            //  LOGOUT CONFIG
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
