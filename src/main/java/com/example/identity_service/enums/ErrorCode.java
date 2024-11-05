package com.example.identity_service.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode
{
    UNCATEGORIZED_EXCEPTION(999, "Uncategorized exception error", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists", HttpStatus.CONFLICT),
    INVALID_DOB(1003, "Date of birth is invalid", HttpStatus.BAD_REQUEST),
    VALIDATION_ERROR(1004, "Validation error", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR(5000, "Internal server error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_CREDENTIALS(5001, "Invalid credentials", HttpStatus.UNAUTHORIZED),
    USER_NOT_AUTHENTICATED(5002, "User not authenticated my bro", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(5003, "You do not permission my bro", HttpStatus.FORBIDDEN);

    private final int code;
    private final String message;
    private final HttpStatusCode httpStatusCode;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode)
    {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
