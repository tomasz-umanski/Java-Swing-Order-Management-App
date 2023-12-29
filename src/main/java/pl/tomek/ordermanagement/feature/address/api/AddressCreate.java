package pl.tomek.ordermanagement.feature.address.api;

import jakarta.validation.constraints.NotBlank;

public record AddressCreate(@NotBlank(message = "StreetName is mandatory")
                            String streetName,
                            @NotBlank(message = "BuildingNumber is mandatory")
                            String buildingNumber,
                            String flatNumber,
                            @NotBlank(message = "City is mandatory")
                            String city,
                            @NotBlank(message = "ZipCode is mandatory")
                            String zipCode,
                            @NotBlank(message = "Voivodeship is mandatory")
                            String voivodeship,
                            @NotBlank(message = "Country is mandatory")
                            String country) {
}
