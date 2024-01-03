package pl.tomek.ordermanagement.backend.feature.orderItem.exception;

import pl.tomek.ordermanagement.backend.validation.ObjectsValidatorException;

import java.util.Set;

public class OrderItemCreateValidatorException extends ObjectsValidatorException {
    public OrderItemCreateValidatorException(Set<String> message) {
        super(message);
    }
}
