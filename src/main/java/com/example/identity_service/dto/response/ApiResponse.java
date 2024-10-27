package com.example.identity_service.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T>
{
    private String status;
    private String message;
    private Integer errorCode;
    private T data;
    private Map<String, String> errors;


    public ApiResponse() {}


    // Constructor for a successful response
    public ApiResponse(String status, String message, T data)
    {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    // Constructor for an error response
    public ApiResponse(String status, String message, Integer errorCode, Map<String, String> errors)
    {
        this.status = status;
        this.message = message;
        this.errorCode = errorCode;
        this.errors = errors;
    }


}
