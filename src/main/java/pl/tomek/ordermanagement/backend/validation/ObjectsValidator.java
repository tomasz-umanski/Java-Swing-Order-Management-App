package pl.tomek.ordermanagement.backend.validation;

import java.util.Set;

public interface ObjectsValidator<T> {
    Set<String> validate(T objectToValidate);
}
