package pl.tomek.ordermanagement.backend.facade.customer.api;

import pl.tomek.ordermanagement.backend.feature.address.api.Address;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;

import java.util.UUID;

public record AddressDto(UUID id,
                         String streetName,
                         String buildingNumber,
                         String flatNumber,
                         String city,
                         String zipCode,
                         String voivodeship,
                         String country) {

    @Override
    public String toString() {
        StringBuilder addressString = new StringBuilder();

        addressString.append(country).append(", ");
        addressString.append(voivodeship).append(", ");
        addressString.append(city).append(", ");
        addressString.append(zipCode).append(", ");
        addressString.append(streetName).append(" ").append(buildingNumber);

        if (flatNumber != null && !flatNumber.isEmpty()) {
            addressString.append("/").append(flatNumber);
        }

        return addressString.toString();
    }

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