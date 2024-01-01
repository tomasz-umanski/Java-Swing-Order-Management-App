INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, deleted)
VALUES (
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
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
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981',
    'John',
    'Doe',
    'ABC Inc.',
    '1234567890',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    FALSE
);

INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, deleted)
VALUES (
    '5e4f3a2b-1c9a-8b7c-6d5e-4f3a2b1c9a8b',
    'Third Street',
    '789',
    NULL,
    'Third City',
    '67890',
    'Third Voivodeship',
    'Third Country',
    FALSE
);

INSERT INTO t_customer (id, name, last_name, company_name, tax_id_number, home_address_id, shipping_address_id, deleted)
VALUES (
    '9a8b7c6d-5e4f-3a2b-1c9a-8b7c6d5e4f3a',
    'Michael',
    'Brown',
    NULL,
    NULL,
    '5e4f3a2b-1c9a-8b7c-6d5e-4f3a2b1c9a8b',
    '5e4f3a2b-1c9a-8b7c-6d5e-4f3a2b1c9a8b',
    FALSE
);

INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, deleted)
VALUES (
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    'Fourth Street',
    '1011',
    NULL,
    'Fourth City',
    '45678',
    'Fourth Voivodeship',
    'Fourth Country',
    FALSE
);

INSERT INTO t_customer (id, name, last_name, company_name, tax_id_number, home_address_id, shipping_address_id, deleted)
VALUES (
    '3b2a1c9a-8b7c-6d5e-4f3a-2b1c9a8b7c6d',
    'Emily',
    'Clark',
    NULL,
    NULL,
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    FALSE
);