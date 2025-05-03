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
    public static final String PRODUCT_API = "/api/products/{productId}";
    public static final String ORDER_API = "/api/orders/{orderId}";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

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
            .requestMatchers("/api/register", "/api/login", "/api/users","/api/reviews",PRODUCT_API,"/api/orders/{orderId}/status").permitAll()

            /* Allow GET requests to specific endpoints for ADMIN and USER roles */
            .requestMatchers(HttpMethod.GET, ORDER_API, "/api/reviews/{reviewId}", "/api/reviews/product/{productId}", "/api/orders").hasAnyRole(ROLE_ADMIN, ROLE_USER)

            /* Allow GET requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.GET, PRODUCT_API).hasRole(ROLE_ADMIN)

            /* Allow PUT requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.PUT, PRODUCT_API, ORDER_API).hasRole(ROLE_ADMIN)

            /* Allow POST requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.POST, "/api/products").hasRole(ROLE_ADMIN)

            /* Allow DELETE requests to specific endpoints for ADMIN role only */
            .requestMatchers(HttpMethod.DELETE, PRODUCT_API).hasRole(ROLE_ADMIN)

            /* Allow POST requests to specific endpoints for USER role only */
            .requestMatchers(HttpMethod.POST, "/api/orders", "/api/reviews").hasRole(ROLE_USER)

            /* Allow GET requests to specific endpoints for USER role only */
            .requestMatchers(HttpMethod.GET, "/api/orders/user/{userId}", "/api/reviews/user/{userId}","api/orders/orderItem/orderId").hasRole(ROLE_USER)

            /* Allow DELETE requests to specific endpoints for USER role only */
            .requestMatchers(HttpMethod.DELETE, ORDER_API, "/api/reviews/{reviewId}").hasRole(ROLE_USER)

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
