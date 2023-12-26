package pl.tomek.ordermanagement.feature.database.api;

import java.util.UUID;

public class BaseEntity {
    protected BaseEntity() {
    }

    protected final UUID id = UUID.randomUUID();

    public UUID getId() {
        return id;
    }
}
