package com.autopass.person.util.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.autopass.person.model.enums.DocumentType;
import com.autopass.person.util.validator.ValidDocument;

public class ValidDocumentValidator implements ConstraintValidator<ValidDocument, String> {

    @Override
    public boolean isValid(String document, ConstraintValidatorContext context) {

        if (document == null)
            return true;

        return DocumentType.findType(document).isPresent();
    }

}
