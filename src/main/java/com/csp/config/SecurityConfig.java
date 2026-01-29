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
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final CustomAuthenticationSuccessHandler successHandler;

    public SecurityConfig(
            CustomUserDetailsService userDetailsService,
            PasswordEncoder passwordEncoder,
            CustomAuthenticationSuccessHandler successHandler) {

        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.successHandler = successHandler;
    }

    // ================= AUTHENTICATION MANAGER =================
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http)
            throws Exception {

        AuthenticationManagerBuilder builder =
                http.getSharedObject(AuthenticationManagerBuilder.class);

        builder
            .userDetailsService(userDetailsService)
            .passwordEncoder(passwordEncoder);

        return builder.build();
    }

    // ================= SECURITY FILTER CHAIN =================
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .authorizeHttpRequests(auth -> auth

                // 🌍 PUBLIC PAGES
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

                // 🌐 STATIC RESOURCES
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()

                // 📄 DOCUMENT ACCESS
                .requestMatchers("/documents/**")
                    .hasAnyRole("CITIZEN", "CLERK", "OFFICER", "ADMIN")

                // 🔐 ROLE-BASED DASHBOARDS
                .requestMatchers("/citizen/**").hasRole("CITIZEN")
                .requestMatchers("/clerk/**").hasRole("CLERK")
                .requestMatchers("/officer/**").hasRole("OFFICER")
                .requestMatchers("/admin/**").hasRole("ADMIN")

                .anyRequest().authenticated()
            )

            // 🔑 LOGIN CONFIG
            .formLogin(form -> form
                .loginPage("/login")
                .successHandler(successHandler)
                .failureUrl("/login?error")
                .permitAll()
            )

            // 🚪 LOGOUT CONFIG
            .logout(logout -> logout
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }
}
