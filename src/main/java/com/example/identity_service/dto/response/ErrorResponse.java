package com.example.identity_service.dto.response;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ErrorResponse
{
    private String message;
    private String details;
}
