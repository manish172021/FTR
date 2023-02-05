package com.ftr.VehicleService.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = IntegerValidator.class)
@Retention(RetentionPolicy.RUNTIME)

public @interface OnlyInteger {

    //default error message
    String message() default "Should be integer";

    // represent group constraint
    Class<?>[] groups() default {};

    // additional information about annotation
    Class<? extends Payload>[] payload() default {};
}