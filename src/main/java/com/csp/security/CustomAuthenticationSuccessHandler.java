package com.csp.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        for (GrantedAuthority authority : authentication.getAuthorities()) {

            String role = authority.getAuthority();

            if (role.equals("ROLE_CITIZEN")) {
                response.sendRedirect("/citizen/dashboard");
                return;
            }

            if (role.equals("ROLE_CLERK")) {
                response.sendRedirect("/clerk/dashboard");
                return;
            }

            if (role.equals("ROLE_OFFICER")) {
                response.sendRedirect("/officer/dashboard");
                return;
            }

            if (role.equals("ROLE_ADMIN")) {
                response.sendRedirect("/admin/dashboard");
                return;
            }
        }

        // fallback
        response.sendRedirect("/login?error");
    }
}
