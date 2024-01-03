package pl.tomek.ordermanagement.backend.validation;

import java.util.Set;

public class ObjectsValidatorException extends RuntimeException {
    public ObjectsValidatorException(Set<String> message) {
        super(String.join("\n", message));
    }

    public ObjectsValidatorException(String message) {
        super(message);
    }
}
