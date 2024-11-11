package com.example.multiservice.validator;


import com.nimbusds.jose.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})// location apply Annotation
@Retention(RetentionPolicy.RUNTIME)// when
@Constraint(
        validatedBy = {}
)
public @interface DobConstraints {
    String message() default "Invalid date of birth";

    int min();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
