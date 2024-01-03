package pl.tomek.ordermanagement.backend.feature.order.exception;

import pl.tomek.ordermanagement.backend.validation.ObjectsValidatorException;

import java.util.Set;

public class OrderCreateValidatorException extends ObjectsValidatorException {
    public OrderCreateValidatorException(Set<String> message) {
        super(message);
    }
}
