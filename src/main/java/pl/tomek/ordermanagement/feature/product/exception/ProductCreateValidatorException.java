package pl.tomek.ordermanagement.feature.product.exception;

import pl.tomek.ordermanagement.feature.validation.ObjectsValidatorException;

import java.util.Set;

public class ProductCreateValidatorException extends ObjectsValidatorException {
    public ProductCreateValidatorException(Set<String> message) {
        super(message);
    }
}
