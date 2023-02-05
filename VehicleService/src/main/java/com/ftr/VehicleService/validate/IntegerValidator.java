package com.ftr.VehicleService.validate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class IntegerValidator implements ConstraintValidator<OnlyInteger, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value.intValue() == value.doubleValue();
    }
}
