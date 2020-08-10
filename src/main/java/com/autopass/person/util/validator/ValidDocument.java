package com.autopass.person.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.autopass.person.util.validator.impl.ValidDocumentValidator;

@Documented
@Constraint(validatedBy = ValidDocumentValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDocument {

    String message() default "Invalid document number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
