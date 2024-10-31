package com.example.multiservice.exception;

import com.example.multiservice.exception.enums.ErrorStatusCode;
import lombok.Data;

@Data
public class AppException extends RuntimeException {

    private ErrorStatusCode errorStatusCode;
    public AppException(ErrorStatusCode errorStatusCode) {
        super(errorStatusCode.getMessage());// extend constructor of runtime exception
        this.errorStatusCode = errorStatusCode;
    }

}
