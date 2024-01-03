package pl.tomek.ordermanagement.backend.facade.product.exception;

import pl.tomek.ordermanagement.backend.validation.ObjectsValidatorException;

import java.util.Set;

public class ProductCreateDtoValidatorException extends ObjectsValidatorException {
    public ProductCreateDtoValidatorException(Set<String> message) {
        super(message);
    }

    public ProductCreateDtoValidatorException(String message) {
        super(message);
    }
}
