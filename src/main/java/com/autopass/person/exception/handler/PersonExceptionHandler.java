package com.autopass.person.exception.handler;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.PRECONDITION_FAILED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import com.autopass.person.exception.HashDoesNotMatchUserException;
import com.autopass.person.exception.InvalidNumberOfFacesException;
import com.autopass.person.exception.InvalidSearchException;
import com.autopass.person.exception.PersonAssociationException;
import com.autopass.person.exception.PersonNotFoundException;
import com.autopass.person.exception.SocialNetworkAlreadyBindedException;
import com.autopass.person.model.response.ErrorResponse;
import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
public class PersonExceptionHandler {

    private final MessageSource messageSource;

    public PersonExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(PersonAssociationException.class)
    @ResponseStatus(code = CONFLICT)
    public ErrorResponse handlePersonAssociationException(PersonAssociationException ex) {
        
        return ErrorResponse.as(message("association.fail"));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = BAD_REQUEST)
    public Collection<ErrorResponse> handleMethodArgumentNotValidExceptionInContext(MethodArgumentNotValidException ex) {
        final var errors = new ArrayList<ErrorResponse>();

        errors.addAll(ex.getBindingResult().getGlobalErrors().stream().map(violation -> ErrorResponse.as(message(violation)))
                .collect(Collectors.toList()));

        errors.addAll(ex.getBindingResult().getFieldErrors().stream()
                .map(violation -> ErrorResponse.as(message(violation)).tag(violation.getField())).collect(Collectors.toList()));

        return errors;
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(code = BAD_REQUEST)
    public Collection<ErrorResponse> handleBindException(BindException ex) {
        return ex.getBindingResult().getFieldErrors().stream()
                .map(violation -> ErrorResponse.as(message(violation)).tag(violation.getField())).collect(Collectors.toList());
    }

    @ExceptionHandler(InvalidSearchException.class)
    @ResponseStatus(code = BAD_REQUEST)
    public ErrorResponse handleInvalidSearchException() {
        return ErrorResponse.as(message("invalid.search"));
    }

    @ExceptionHandler(SocialNetworkAlreadyBindedException.class)
    @ResponseStatus(code = CONFLICT)
    public ErrorResponse handleSocialNetworkAlreadyBindedException(SocialNetworkAlreadyBindedException ex) {
        return ErrorResponse.as(message("socialNetwork.alreadyBinded", message(ex.getSocialNetwork().toString().toLowerCase())));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<Object> handlePersonNotFoundException() {
        return ResponseEntity.status(BAD_REQUEST).build();    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(code = INTERNAL_SERVER_ERROR)
    public ErrorResponse handleRuntimeException() {
        return ErrorResponse.as(message("error.NotMapped"));
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(code = BAD_REQUEST)
    public ErrorResponse handleArgumentTypeMismatch() {
        return ErrorResponse.as(message("error.typeMismatch"));
    }

    @ExceptionHandler(HashDoesNotMatchUserException.class)
    @ResponseStatus(code = CONFLICT)
    public ErrorResponse handleHashDoesNotMatchUserException() {
        return ErrorResponse.as(message("email.invalidHash"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(code = BAD_REQUEST)
    public ErrorResponse handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        if (ex.getRootCause() instanceof JsonMappingException) {
            return handleJsonMappingException((JsonMappingException) ex.getRootCause());
        }

        return ErrorResponse.as(message("message.messageNotReadable"));
    }


    private String message(MessageSourceResolvable violation) {
        return messageSource.getMessage(violation, LocaleContextHolder.getLocale());
    }

    private String message(String code, Object... params) {
        return messageSource.getMessage(code, params, LocaleContextHolder.getLocale());
    }

    private ErrorResponse handleJsonMappingException(JsonMappingException ex) {
        final String paramName = ex.getPath().stream()
                .map(ref -> Optional.ofNullable(ref.getFieldName()).orElse(String.format("[%s]", ref.getIndex())))
                .collect(Collectors.joining("."));

        return ErrorResponse
                .as(messageSource.getMessage("message.invalidParameterValue", new Object[] { paramName }, LocaleContextHolder.getLocale()))
                .tag(paramName);
    }
}
