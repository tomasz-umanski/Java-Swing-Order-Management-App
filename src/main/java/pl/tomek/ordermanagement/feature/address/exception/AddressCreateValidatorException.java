package pl.tomek.ordermanagement.feature.address.exception;

import pl.tomek.ordermanagement.feature.validation.ObjectsValidatorException;

import java.util.Set;

public class AddressCreateValidatorException extends ObjectsValidatorException {
    public AddressCreateValidatorException(Set<String> message) {
        super(message);
    }
}
