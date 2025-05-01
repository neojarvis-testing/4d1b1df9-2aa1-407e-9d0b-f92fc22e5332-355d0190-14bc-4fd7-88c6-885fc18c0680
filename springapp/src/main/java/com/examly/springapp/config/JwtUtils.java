package com.examly.springapp.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class JwtUtils {

    /* Inject the secret key from the application properties */
    @Value("${SECRET_KEY}") // in application property add SECRET_KEY
    private String SECRET_KEY;

    /* Generate a JWT token based on the authentication object */
    public String genrateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(System.currentTimeMillis() + (3*60 * 60 * 1000))) // Token valid for 30 minutes
            .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
            .compact();
    }

    /* Extract the username from the JWT token */
    public String extractUsername(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    /* Extract the expiration date from the JWT token */
    public Date extractExperation(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getExpiration();
    }

    /* Check if the JWT token is expired */
    public boolean isTokenExpired(String token) {
        Date expire = extractExperation(token);
        return expire.before(new Date());
    }

    /* Extract the JWT token from the request header */
    public String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    /* Validate the JWT token by checking its expiration */
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}