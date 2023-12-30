package pl.tomek.ordermanagement.backend.feature.product.exception;

import pl.tomek.ordermanagement.backend.feature.validation.ObjectsValidatorException;

import java.util.Set;

public class ProductCreateValidatorException extends ObjectsValidatorException {
    public ProductCreateValidatorException(Set<String> message) {
        super(message);
    }
}
