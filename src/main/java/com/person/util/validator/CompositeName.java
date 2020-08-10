package com.person.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.person.util.validator.impl.CompositeNameValidator;

@Documented
@Constraint(validatedBy = CompositeNameValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CompositeName {

    String message() default "Name is not composite";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
