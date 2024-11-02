package com.example.identity_service.config;


import com.example.identity_service.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class WebSecurityConfig
{
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final String[] PUBLIC_ENDPOINTS = {
            "/api/**",
            "/auth/token",
            "/auth/introspect",
    };

    @Bean
    public BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        http
                .csrf(AbstractHttpConfigurer::disable)  // Disable CSRF if using JWT or other stateless authentication
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST,PUBLIC_ENDPOINTS).permitAll()  // Allow access to public endpoints
                        .anyRequest().authenticated()               // Require authentication for other endpoints
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);  // JWT filter


        return http.build();
    }
}
