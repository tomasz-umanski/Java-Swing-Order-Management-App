package pl.tomek.ordermanagement.feature.database.api;

import java.util.UUID;

public class BaseEntity {
    protected final UUID id = UUID.randomUUID();

    protected BaseEntity() {
    }

    public UUID getId() {
        return id;
    }
}
