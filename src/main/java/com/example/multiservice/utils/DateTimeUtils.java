package com.example.multiservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Component
public class DateTimeUtils {

    @Named("parseStringToLocalDateTime")
    public LocalDateTime parseStringToLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString);
    }
}
