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
public SecurityFilterChain cFilterChain(HttpSecurity http)throws Exception{
     http.cors(cors->cors.disable())
    .csrf(csrf->csrf.disable())
    .authorizeHttpRequests(auth->auth
    .requestMatchers("/api/register","/api/login","/api/users","/api/products","/api/reviews","/api/reviews/product/{productId}").permitAll()
    .requestMatchers(HttpMethod.GET,"/api/orders/{orderId}","/api/reviews/{reviewId}").hasAnyRole("ADMIN","USER")
    .requestMatchers(HttpMethod.GET,"/api/products/{productId}","/api/orders").hasRole("ADMIN")
    .requestMatchers(HttpMethod.PUT,"/api/products/{productId}","/api/orders/{orderId}").hasRole("ADMIN")
    .requestMatchers(HttpMethod.POST,"/api/products").hasRole("ADMIN")
    .requestMatchers(HttpMethod.DELETE,"/api/products/{productId}").hasRole("ADMIN")
    .requestMatchers(HttpMethod.POST,"/api/orders","/api/reviews").hasRole("USER")
    .requestMatchers(HttpMethod.GET,"/api/orders/user/{userId}","/api/reviews/user/{userId}").hasRole("USER")
    .requestMatchers(HttpMethod.DELETE,"/api/orders/{orderId}","/api/reviews/{reviewId}").hasRole("USER")
    .anyRequest().authenticated())
    .exceptionHandling(exception->exception.authenticationEntryPoint(entryPoint))
    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    
    return http.build();
}
}