package com.example.multiservice.exception;


import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Collect all Exception for easy management, For Exception except what we customize
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRunTimeException(RuntimeException runtimeException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(ErrorStatusCode.UNCATEGORIZED_EXCEPTION.getStatusCode());
        apiResponse.setMessage(ErrorStatusCode.UNCATEGORIZED_EXCEPTION.getMessage());
            return ResponseEntity.badRequest().body(apiResponse);
    }

// Solve Exception With AppException through Enum(ErrorStatusCode)
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException) {
        ErrorStatusCode errorStatusCode = appException.getErrorStatusCode();// when catch exception, we have pass parameter with Type ErrorStatusCode, so that we can get statusCode And Message
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setStatusCode(errorStatusCode.getStatusCode());
        apiResponse.setMessage(errorStatusCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {

        String errorMessage = methodArgumentNotValidException.getFieldError().getDefaultMessage();// get message form @Valid

        ErrorStatusCode errorStatusCode = ErrorStatusCode.INVALID_KEY_EXCEPTION;// Default value with Error Incorrect Message Key In @Valid

        try {
            errorStatusCode = ErrorStatusCode.valueOf(errorMessage);// Parse Enum Through Name of Enum, then get correct message
        }catch (IllegalArgumentException e) {}




        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setStatusCode(errorStatusCode.getStatusCode());
        apiResponse.setMessage(errorStatusCode.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

}
