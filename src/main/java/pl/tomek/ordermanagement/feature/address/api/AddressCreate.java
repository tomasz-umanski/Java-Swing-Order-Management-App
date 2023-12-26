package pl.tomek.ordermanagement.feature.address.api;

public record AddressCreate(String streetName,
                            String buildingNumber,
                            String flatNumber,
                            String city,
                            String zipCode,
                            String voivodeship,
                            String country) {
}
