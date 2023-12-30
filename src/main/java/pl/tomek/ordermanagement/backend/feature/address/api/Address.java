package pl.tomek.ordermanagement.backend.feature.address.api;

import java.util.UUID;

public record Address(UUID id,
                      String streetName,
                      String buildingNumber,
                      String flatNumber,
                      String city,
                      String zipCode,
                      String voivodeship,
                      String country) {
}