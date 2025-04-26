package com.examly.springapp.config;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


/* This class is a custom implementation of the AuthenticationEntryPoint interface */
/* It handles unauthorized access attempts in a Spring Security context */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException authException) throws IOException, ServletException {

        /* This method is triggered whenever an unauthenticated user tries to access a secured resource */
        /* It sends a 403 Forbidden error response with the message "Unauthorized" */

        response.sendError(HttpServletResponse.SC_FORBIDDEN,"Unauthorized");
    }
}