INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, deleted)
VALUES (
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545', -- Replace with your UUID for the address ID
    'Sample Street',
    '123',
    'Apt 456',
    'Sample City',
    '12345',
    'Sample Voivodeship',
    'Sample Country',
    FALSE
);

INSERT INTO t_customer (id, name, last_name, company_name, tax_id_number, home_address_id, shipping_address_id, deleted)
VALUES (
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981', -- Replace with your UUID for the customer ID
    'John',
    'Doe',
    'ABC Inc.',
    '1234567890',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545', -- Replace with the corresponding home address UUID
    NULL, -- Replace with the corresponding shipping address UUID if available, otherwise use NULL
    FALSE
);
