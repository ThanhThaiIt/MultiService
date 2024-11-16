package com.example.multiservice.exception;

import java.util.Map;
import java.util.Objects;

import jakarta.validation.ConstraintViolation;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.exception.enums.ErrorStatusCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {
    // attribute in group Map<String,Object>
    private static final String MIN_ATTRIBUTE = "min";
    private static final String MAX_ATTRIBUTE = "max";

    // Collect all Exception for easy management, For Exception except what we customize
    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handlingRunTimeException(RuntimeException runtimeException) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(ErrorStatusCode.UNCATEGORIZED_EXCEPTION.getCode());
        apiResponse.setMessage(runtimeException.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    // Solve Exception With AppException through Enum(ErrorStatusCode)
    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handlingAppException(AppException appException) {
        ErrorStatusCode errorStatusCode =
                appException.getErrorStatusCode(); // when catch exception, we have pass parameter with Type
        // ErrorStatusCode, so
        // that we can get statusCode And Message
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setCode(errorStatusCode.getCode());
        apiResponse.setMessage(errorStatusCode.getMessage());
        return ResponseEntity.status(errorStatusCode.getHttpStatusCode()).body(apiResponse);
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    ResponseEntity<ApiResponse> handlingAccessDeniedException(AccessDeniedException accessDeniedException) {

        ErrorStatusCode errorStatusCode = ErrorStatusCode.UNAUTHORIZED_CLIENT;
        return ResponseEntity.status(errorStatusCode.getHttpStatusCode())
                .body(ApiResponse.builder()
                        .code(errorStatusCode.getCode())
                        .message(errorStatusCode.getMessage())
                        .build());
    }

    // Throw exception with validation request

    // example: default message [DOB_EXCEPTION] we will use message to get From enum ErrorStatusCode
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handlingMethodArgumentNotValidException(
            MethodArgumentNotValidException methodArgumentNotValidException) {

        String errorMessage =
                methodArgumentNotValidException.getFieldError().getDefaultMessage(); // get message form @Valid

        ErrorStatusCode errorStatusCode =
                ErrorStatusCode.INVALID_KEY_EXCEPTION; // Default value with Error Incorrect Message Key In @Valid

        Map<String, Object> attributes = null;
        try {
            errorStatusCode =
                    ErrorStatusCode.valueOf(errorMessage); // Parse Enum Through Name of Enum, then get correct message

            // advance: replace value in message exception
            // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/validation/BindException.html
            var constraintViolations = methodArgumentNotValidException
                    .getBindingResult()
                    .getAllErrors()
                    .getFirst()
                    .unwrap(ConstraintViolation.class);

            attributes = constraintViolations.getConstraintDescriptor().getAttributes();
            log.info(attributes.toString());

            //             for (var x : methodArgumentNotValidException.getBindingResult().getAllErrors()){
            //                 var xNew = x.unwrap(ConstraintViolation.class);
            //                 log.info(xNew.getConstraintDescriptor().getAttributes().toString());
            //             }

        } catch (IllegalArgumentException e) {
        }
        ApiResponse apiResponse = new ApiResponse();

        apiResponse.setCode(errorStatusCode.getCode());
        apiResponse.setMessage(
                Objects.nonNull(attributes)
                        ? mapAttribute(errorStatusCode.getMessage(), attributes)
                        : errorStatusCode.getMessage());
        // apiResponse.setMessage(methodArgumentNotValidException.getMessage());
        return ResponseEntity.badRequest().body(apiResponse);
    }

    private String mapAttribute(String message, Map<String, Object> attributes) {

        String minValue = String.valueOf(attributes.get(MIN_ATTRIBUTE));
        String maxValue = String.valueOf(attributes.get(MAX_ATTRIBUTE));

        message = message.replace("{" + MIN_ATTRIBUTE + "}", minValue);
        message = message.replace("{" + MAX_ATTRIBUTE + "}", maxValue);

        return message;
    }
}
