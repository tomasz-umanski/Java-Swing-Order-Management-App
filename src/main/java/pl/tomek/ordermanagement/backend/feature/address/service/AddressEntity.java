package pl.tomek.ordermanagement.backend.feature.address.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import pl.tomek.ordermanagement.backend.feature.address.api.Address;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;

import java.util.UUID;

import static java.lang.Boolean.FALSE;

@Entity
@Table(name = "t_address")
@SQLDelete(sql = "UPDATE t_address SET deleted = true WHERE id=?")
@SQLRestriction("deleted=false")
class AddressEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    @NotNull
    private String streetName;
    @NotNull
    private String buildingNumber;
    private String flatNumber;
    @NotNull
    private String city;
    @NotNull
    private String zipCode;
    @NotNull
    private String voivodeship;
    @NotNull
    private String country;
    @NotNull
    private boolean deleted = FALSE;

    public static AddressEntity of(AddressCreate addressCreate) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.streetName = addressCreate.streetName();
        addressEntity.buildingNumber = addressCreate.buildingNumber();
        addressEntity.flatNumber = addressCreate.flatNumber();
        addressEntity.city = addressCreate.city();
        addressEntity.zipCode = addressCreate.zipCode();
        addressEntity.voivodeship = addressCreate.voivodeship();
        addressEntity.country = addressCreate.country();
        return addressEntity;
    }

    public Address toDomain() {
        return new Address(
                this.id,
                this.streetName,
                this.buildingNumber,
                this.flatNumber,
                this.city,
                this.zipCode,
                this.voivodeship,
                this.country
        );
    }
}
