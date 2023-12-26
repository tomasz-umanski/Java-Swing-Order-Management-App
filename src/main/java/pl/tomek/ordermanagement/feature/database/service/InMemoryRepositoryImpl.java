package pl.tomek.ordermanagement.feature.database.service;

import org.springframework.stereotype.Service;
import pl.tomek.ordermanagement.feature.database.api.BaseEntity;
import pl.tomek.ordermanagement.feature.database.api.InMemoryRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
class InMemoryRepositoryImpl<T extends BaseEntity> implements InMemoryRepository<T> {

    protected final Map<UUID, T> database = new HashMap<>();

    @Override
    public T create(T entity) {
        database.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public void delete(UUID id) {
        database.remove(id);
    }

    @Override
    public T getById(UUID id) {
        return database.get(id);
    }
}
