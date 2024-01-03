package pl.tomek.ordermanagement.backend.feature.customer.exception;

import pl.tomek.ordermanagement.backend.validation.ObjectsValidatorException;

import java.util.Set;

public class CustomerCreateValidatorException extends ObjectsValidatorException {
    public CustomerCreateValidatorException(Set<String> message) {
        super(message);
    }
}
