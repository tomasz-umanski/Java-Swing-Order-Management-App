package pl.tomek.ordermanagement.backend.feature.validation;

import java.util.Set;

public class ObjectsValidatorException extends RuntimeException {
    public ObjectsValidatorException(Set<String> message) {
        super(String.join("|", message));
    }
}
