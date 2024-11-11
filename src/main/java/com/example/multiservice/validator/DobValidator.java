package com.example.multiservice.validator;

import com.example.multiservice.validator.constraints.DobConstraints;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.annotation.Annotation;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DobValidator implements ConstraintValidator<DobConstraints, LocalDate> {


    private int min;

    @Override// init
    public void initialize(DobConstraints constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(value)) {
            return true;//We will allow the user to enter null, since they probably won't want to enter a date of birth.
        }

        //long userCurrentAge = LocalDate.now().getYear() - value.getYear();
        long userCurrentAge = ChronoUnit.YEARS.between(value, LocalDate.now());

        return userCurrentAge >= min;
    }
}
