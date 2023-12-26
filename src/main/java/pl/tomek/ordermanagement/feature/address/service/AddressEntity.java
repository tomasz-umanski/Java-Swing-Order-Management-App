package pl.tomek.ordermanagement.feature.address.service;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;

import java.util.UUID;

@Entity
@Table(name = "t_address")
class AddressEntity {
    @Id
    private final UUID id = UUID.randomUUID();
    private String streetName;
    private String buildingNumber;
    private String flatNumber;
    private String city;
    private String zipCode;
    private String voivodeship;
    private String country;

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
