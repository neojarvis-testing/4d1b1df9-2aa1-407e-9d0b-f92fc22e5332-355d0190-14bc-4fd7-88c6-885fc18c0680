package com.examly.springapp.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import com.examly.springapp.service.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    UserServiceImpl service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        /* Extract the token from the request */
        String token =jwtUtils.extractToken(request);

        /* Check if the token is not null and is valid */
        if(token!=null&& jwtUtils.validateToken(token)){

            /* Extract the username from the token */
            String username = jwtUtils.extractUsername(token);

            /* Load user details using the username */
            UserDetails userDetails = service.loadUserByUsername(username);

            /* Create an authentication token with the user details and authorities */
            UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            
            /* Set additional details for the authentication token */
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            /* Set the authentication in the security context */
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    /* Continue the filter chain */
    filterChain.doFilter(request, response);
}

}