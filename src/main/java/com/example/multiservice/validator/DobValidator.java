package com.example.multiservice.validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.multiservice.validator.constraints.DobConstraints;

public class DobValidator implements ConstraintValidator<DobConstraints, LocalDate> {

    private int min;
    private int max;

    @Override // init
    public void initialize(DobConstraints constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
        max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(value)) {
            return true; // Allow null date of birth (user might choose not to provide it)
        }
        // Calculate the user's age based on the date of birth
        // long userCurrentAge = LocalDate.now().getYear() - value.getYear();
        long userCurrentAge = ChronoUnit.YEARS.between(value, LocalDate.now());

        return userCurrentAge >= min && userCurrentAge <= max;
    }

    /**
     * Method to parse age limits from a "min-max" formatted string.
     * @param range String in the format "min-max".
     * @return An integer array containing min and max values, or null if format is invalid.
     */
    private int[] parseAgeRange(String range) {
        if (range == null || !range.matches("\\d+-\\d+")) {
            return null; // Return null if format is incorrect, example: 16-80
        }

        String[] minMax = range.split("-");
        int min = Integer.parseInt(minMax[0].trim());
        int max = Integer.parseInt(minMax[1].trim());

        return new int[] {min, max};
    }
}
