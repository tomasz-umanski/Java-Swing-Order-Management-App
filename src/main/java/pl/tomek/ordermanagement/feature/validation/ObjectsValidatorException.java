package pl.tomek.ordermanagement.feature.validation;

import java.util.Set;

public class ObjectsValidatorException extends RuntimeException {
    public ObjectsValidatorException(Set<String> message) {
        super(String.join("|", message));
    }
}
