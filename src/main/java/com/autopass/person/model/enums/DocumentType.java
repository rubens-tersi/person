package com.autopass.person.model.enums;

import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;

import com.autopass.person.util.validator.impl.ValidCpfValidator;

public enum DocumentType {

    CPF {
        @Override
        public boolean isValid(String document) {
            return new ValidCpfValidator().isValid(document, null);
        }
    },
    PASSPORT {
        @Override
        public boolean isValid(String document) {
            return Pattern.compile("[\\dA-Z]{2}\\d{6}").matcher(document).matches();
        }
    };

    public abstract boolean isValid(String document);

    public static Optional<DocumentType> findType(String document) {
        return Arrays.stream(values()).filter(type -> type.isValid(document)).findFirst();
    }

}
