package com.example.identity_service.enums;

import lombok.Getter;

@Getter
public enum ErrorCode
{
    UNCATEGORIZED_EXCEPTION(999,"uncategorized exception error"),
    USER_NOT_FOUND(1001, "User not found sad boy"),
    USERNAME_ALREADY_EXISTS(1002, "Username already exists"),
    INVALID_DOB(1003, "Date of birth is invalid"),
    VALIDATION_ERROR(1004, "Validation error"),
    INTERNAL_SERVER_ERROR(5000, "Internal server error");


    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
