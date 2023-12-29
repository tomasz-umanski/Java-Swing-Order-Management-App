package pl.tomek.ordermanagement.feature.order.exception;

import pl.tomek.ordermanagement.feature.validation.ObjectsValidatorException;

import java.util.Set;

public class OrderCreateValidatorException extends ObjectsValidatorException {
    public OrderCreateValidatorException(Set<String> message) {
        super(message);
    }
}
