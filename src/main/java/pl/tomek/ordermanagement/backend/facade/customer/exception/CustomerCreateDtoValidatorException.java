package pl.tomek.ordermanagement.backend.facade.customer.exception;

import pl.tomek.ordermanagement.backend.validation.ObjectsValidatorException;

import java.util.Set;

public class CustomerCreateDtoValidatorException extends ObjectsValidatorException {
    public CustomerCreateDtoValidatorException(Set<String> message) {
        super(message);
    }
}
