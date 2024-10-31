package com.example.multiservice.exception.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ErrorStatusCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error"),
    INVALID_KEY_EXCEPTION(1001, "Invalid Message Key Exception"),// Incorrect Message Key In @Valid

    // User
    USER_NOT_FOUND(1000, "User Not Found"),
    USER_ALREADY_EXISTS(1006, "User Already Exists"),
    USER_FIRST_NAME_INVALID(1002, "First Name must be At Least 6 characters and Maximum 9 characters"),
    USER_MIDDLE_NAME_INVALID(1003, "Middle Name must be At Least 6 characters and Maximum 9 characters"),
    USER_LAST_NAME_INVALID(1004, "Last Name must be At Least 6 characters and Maximum 9 characters"),
    INVALID_PASSWORD(1005, "Password must be At Least 8 character"),

    // Authen

    UNAUTHENTICATED(1007, "Unauthenticated Error"),
    IN_CORRECT_FORMAT_JWT(1008, "InCorrect Format JWT"),
    TOKEN_EXPIRED(1009, "Token Expired, Please Sign-In again"),

    ;

    private int statusCode;
    private String message;

    public int getStatusCode() {
        return statusCode;
    }

    public String getMessage() {
        return message;
    }
}
