package com.person.util.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.person.util.validator.CompositeName;

public class CompositeNameValidator implements ConstraintValidator<CompositeName, String> {

    @Override
    public boolean isValid(String name, ConstraintValidatorContext context) {
        if (name == null)
            return true;

        return name.split(" ").length > 1;
    }
}
