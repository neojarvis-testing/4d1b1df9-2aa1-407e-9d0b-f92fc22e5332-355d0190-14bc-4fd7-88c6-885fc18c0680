package com.examly.springapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
@Autowired
JwtAuthenticationEntryPoint entryPoint;
@Autowired
UserDetailsService userDetailsService;
@Autowired
PasswordEncoder encoder;
@Autowired
JwtAuthenticationFilter filter;
@Autowired
public void configure(AuthenticationManagerBuilder authority)throws Exception{
    authority.userDetailsService(userDetailsService).passwordEncoder(encoder);
}
@Bean
public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
    return http.getSharedObject(AuthenticationManagerBuilder.class)
    .userDetailsService(userDetailsService)
    .passwordEncoder(encoder)
    .and()
    .build();
}
@Bean
public SecurityFilterChain cFilterChain(HttpSecurity http) throws Exception {
    /* Configure CORS settings */
    http.cors(cors -> cors.disable())
        /* Disable CSRF protection */
        .csrf(csrf -> csrf.disable())
        /* Configure authorization rules */
        .authorizeHttpRequests(auth -> auth

            /* Allow public access to registration, login, and user endpoints */
            .requestMatchers("/api/register", "/api/login", "/api/users","/api/reviews", "/api/products").permitAll()

            /* Allow GET requests to specific endpoints for ADMIN and USER roles */
            .requestMatchers(HttpMethod.GET, "/api/orders/{orderId}", "/api/reviews/{reviewId}", "/api/reviews/product/{productId}", "/api/orders").hasAnyRole("ADMIN", "USER")

            /* Allow GET requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.GET, "/api/products/{productId}").hasRole("ADMIN")

            /* Allow PUT requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.PUT, "/api/products/{productId}", "/api/orders/{orderId}").hasRole("ADMIN")

            /* Allow POST requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.POST, "/api/products").hasRole("ADMIN")

            /* Allow DELETE requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.DELETE, "/api/products/{productId}").hasRole("ADMIN")

            /* Allow POST requests to specific endpoints for USER role only */
            .requestMatchers(HttpMethod.POST, "/api/orders", "/api/reviews").hasRole("USER")

            /* Allow GET requests to specific endpoints for USER role only */
            .requestMatchers(HttpMethod.GET, "/api/orders/user/{userId}", "/api/reviews/user/{userId}").hasRole("USER")

            /* Allow DELETE requests to specific endpoints for USER role only */
            .requestMatchers(HttpMethod.DELETE, "/api/orders/{orderId}", "/api/reviews/{reviewId}").hasRole("USER")

            /* Allow all other requests */
            .anyRequest().permitAll())

        /* Configure exception handling */
        .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint))
        /* Add custom filter before the UsernamePasswordAuthenticationFilter */
        .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

    /* Build and return the SecurityFilterChain */
    return http.build();
}
}