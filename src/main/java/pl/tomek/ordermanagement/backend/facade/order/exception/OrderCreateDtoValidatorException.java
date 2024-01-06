package pl.tomek.ordermanagement.backend.facade.order.exception;

import pl.tomek.ordermanagement.backend.validation.ObjectsValidatorException;

import java.util.Set;

public class OrderCreateDtoValidatorException extends ObjectsValidatorException {
    public OrderCreateDtoValidatorException(Set<String> message) {
        super(message);
    }
}
