package pl.tomek.ordermanagement.facade.api;

import pl.tomek.ordermanagement.feature.address.api.Address;
import pl.tomek.ordermanagement.feature.address.api.AddressCreate;

public record AddressDto(String streetName,
                         String buildingNumber,
                         String flatNumber,
                         String city,
                         String zipCode,
                         String voivodeship,
                         String country) {

    public static AddressDto of(Address address) {
        return new AddressDto(
                address.streetName(),
                address.buildingNumber(),
                address.flatNumber(),
                address.city(),
                address.zipCode(),
                address.voivodeship(),
                address.country()
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