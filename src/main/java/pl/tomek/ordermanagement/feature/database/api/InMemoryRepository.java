package pl.tomek.ordermanagement.feature.database.api;

import java.util.UUID;

public interface InMemoryRepository<T extends BaseEntity> {
    T create(T entity);

    void delete(UUID id);

    T getById(UUID id);
}
