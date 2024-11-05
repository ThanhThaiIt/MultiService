package com.example.multiservice.exception.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@AllArgsConstructor
@Getter
public enum ErrorStatusCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized Error", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_KEY_EXCEPTION(1001, "Invalid Message Key Exception",HttpStatus.BAD_REQUEST),// Incorrect Message Key In @Valid

    // User
    USER_NOT_FOUND(1000, "User Not Found",HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS(1006, "User Already Exists",HttpStatus.BAD_REQUEST),
    USER_FIRST_NAME_INVALID(1002, "First Name must be At Least 6 characters and Maximum 9 characters",HttpStatus.BAD_REQUEST),
    USER_MIDDLE_NAME_INVALID(1003, "Middle Name must be At Least 6 characters and Maximum 9 characters",HttpStatus.BAD_REQUEST),
    USER_LAST_NAME_INVALID(1004, "Last Name must be At Least 6 characters and Maximum 9 characters",HttpStatus.BAD_REQUEST),
    INVALID_PASSWORD(1005, "Password must be At Least 8 character",HttpStatus.BAD_REQUEST),

    // Authen

    UNAUTHENTICATED(1007, "Unauthenticated Error",HttpStatus.UNAUTHORIZED),
    IN_CORRECT_FORMAT_JWT(1008, "InCorrect Format JWT",HttpStatus.BAD_REQUEST),
    TOKEN_EXPIRED(1009, "Token Expired, Please Sign-In again",HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED_CLIENT(10010, "User Do Not Have Permission",HttpStatus.FORBIDDEN),

    ;

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;


}
