package pl.tomek.ordermanagement.feature.orderItem.exception;

import pl.tomek.ordermanagement.feature.validation.ObjectsValidatorException;

import java.util.Set;

public class OrderItemCreateValidatorException extends ObjectsValidatorException {
    public OrderItemCreateValidatorException(Set<String> message) {
        super(message);
    }
}
