package com.ftr.TerminalService.validate;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;


@Documented
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Constraint(validatedBy = CountryValidator.class)
@Retention(RetentionPolicy.RUNTIME)

public @interface ValidCountry {

    //default error message
    String message() default "Invalid country";

    // represent group constraint
    Class<?>[] groups() default {};

    // additional information about annotation
    Class<? extends Payload>[] payload() default {};
}