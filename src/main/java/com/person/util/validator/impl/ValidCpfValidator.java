package com.person.util.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.person.util.validator.ValidCPF;

import br.com.caelum.stella.validation.CPFValidator;

public class ValidCpfValidator implements ConstraintValidator<ValidCPF, String> {

    private final CPFValidator cpfValidator = new CPFValidator(false);

    @Override
    public boolean isValid(String cnpj, ConstraintValidatorContext context) {
        if (null == cnpj)
            return true;

        return validateCpf(cnpj);
    }

    private boolean validateCpf(String cnpj) {
        try {
            cpfValidator.assertValid(cnpj);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
