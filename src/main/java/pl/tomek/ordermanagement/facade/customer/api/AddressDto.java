package pl.tomek.ordermanagement.facade.customer.api;

import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;

import java.util.UUID;

public record AddressDto(UUID id,
                         String streetName,
                         String buildingNumber,
                         String flatNumber,
                         String city,
                         String zipCode,
                         String voivodeship,
                         String country) {

    public static AddressDto of(Address address) {
        return new AddressDto(
                address.id(),
                address.streetName(),
                address.buildingNumber(),
                address.flatNumber(),
                address.city(),
                address.zipCode(),
                address.voivodeship(),
                address.country()
        );
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

    public AddressCreate toCreate() {
        return new AddressCreate(
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