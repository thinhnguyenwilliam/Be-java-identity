package com.example.identity_service.exception;


import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.enums.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;



import java.io.IOException;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException
    {
        ErrorCode errorCode = ErrorCode.USER_NOT_AUTHENTICATED;  // Adjust this to match your ErrorCode enum for Unauthorized access
        response.setStatus(errorCode.getHttpStatusCode().value());
        response.setContentType("application/json");

        // Create an ApiResponse instance with error details
        ApiResponse<Void> apiResponse = new ApiResponse<>();
        apiResponse.setErrorCode(errorCode.getCode());
        apiResponse.setMessage(errorCode.getMessage());


        // Convert ApiResponse to JSON and write it to the response
        String jsonResponse = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(jsonResponse);
        response.flushBuffer();// Ensure the response is sent immediately
    }
}
