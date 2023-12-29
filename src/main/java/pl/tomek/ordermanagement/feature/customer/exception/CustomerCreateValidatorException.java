package pl.tomek.ordermanagement.feature.customer.exception;

import pl.tomek.ordermanagement.feature.validation.ObjectsValidatorException;

import java.util.Set;

public class CustomerCreateValidatorException extends ObjectsValidatorException {
    public CustomerCreateValidatorException(Set<String> message) {
        super(message);
    }
}
