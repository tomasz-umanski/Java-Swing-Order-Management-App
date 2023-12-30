package pl.tomek.ordermanagement.backend.feature.validation;

import java.util.Set;

public interface ObjectsValidator<T> {
    public Set<String> validate(T objectToValidate);
}
