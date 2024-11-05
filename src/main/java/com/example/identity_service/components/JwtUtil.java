package com.example.identity_service.components;
import com.example.identity_service.enums.Role;
import com.example.identity_service.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component
public class JwtUtil
{
    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${jwt.expiration}")
    private Long expiration;

    private final SecretKey secretKey;

    public JwtUtil(@Value("${jwt.secretKey}") String secret)
    {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }




    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("roles", user.getRoles().stream().map(Role::name).collect(Collectors.toList()))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }



    @SuppressWarnings("unchecked")
    public Set<Role> getRolesFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        List<String> roles = claims.get("roles", List.class);
        return roles.stream()
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }

    /**
     * Verifies the given token.
     * @param token The JWT token to verify.
     * @return True if the token is valid, false otherwise.
     */
    public Boolean verifyToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token is invalid or expired
            logger.error("Invalid or expired JWT token", e);
            return false;
        }
    }


    /**
     * Validates the token against the username and checks expiration.
     * @param token The JWT token.
     * @param username The username to validate against.
     * @return True if valid, false otherwise.
     */
    public Boolean validateToken(String token, String username) {
        String tokenUsername = getUsernameFromToken(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getSubject();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getClaimsFromToken(token).getExpiration();
        return expiration.before(new Date());
    }

    private Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
