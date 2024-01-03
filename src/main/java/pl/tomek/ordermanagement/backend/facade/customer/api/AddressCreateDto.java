package pl.tomek.ordermanagement.backend.facade.customer.api;

import jakarta.validation.constraints.NotBlank;
import pl.tomek.ordermanagement.backend.feature.address.api.AddressCreate;

public record AddressCreateDto(@NotBlank(message = "Street Name is mandatory")
                               String streetName,
                               @NotBlank(message = "Building Number is mandatory")
                               String buildingNumber,
                               String flatNumber,
                               @NotBlank(message = "City is mandatory")
                               String city,
                               @NotBlank(message = "Zip Code is mandatory")
                               String zipCode,
                               @NotBlank(message = "Voivodeship is mandatory")
                               String voivodeship,
                               @NotBlank(message = "Country is mandatory")
                               String country) {
    public AddressCreate toDomainCreate() {
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
