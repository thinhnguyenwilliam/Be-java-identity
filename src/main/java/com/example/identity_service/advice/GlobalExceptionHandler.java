package com.example.identity_service.advice;

import com.example.identity_service.dto.response.ApiResponse;
import com.example.identity_service.enums.ErrorCode;
import com.example.identity_service.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler
{
    //RuntimeException
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex)
    {

        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }




    // Handle AppException
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ApiResponse<Void>> handleAppException(AppException ex)
    {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(ex.getErrorCode().getCode());
        response.setMessage(ex.getErrorCode().getMessage());

        // Dynamically retrieve HttpStatus from the ErrorCode
        HttpStatusCode httpStatus = ex.getErrorCode().getHttpStatusCode();

        return new ResponseEntity<>(response, httpStatus);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex)
    {
        ApiResponse<Void> response = new ApiResponse<>();
        response.setErrorCode(ErrorCode.UNAUTHORIZED.getCode());
        response.setMessage(ErrorCode.UNAUTHORIZED.getMessage());

        // Dynamically retrieve HttpStatus from the ErrorCode
        HttpStatusCode httpStatus = ErrorCode.UNAUTHORIZED.getHttpStatusCode();

        return new ResponseEntity<>(response, httpStatus);
    }


    // Handle MethodArgumentNotValidException for validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> handleValidationExceptions(MethodArgumentNotValidException ex)
    {
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }


        ApiResponse<Void> errorResponse = new ApiResponse<>(
                "error",
                "Validation failed",
                ErrorCode.VALIDATION_ERROR.getCode(),
                errors
        );

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
