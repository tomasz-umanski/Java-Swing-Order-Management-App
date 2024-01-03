INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, archived)
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

INSERT INTO t_customer (id, name, last_name, company_name, tax_id_number, home_address_id, shipping_address_id, archived)
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

INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, archived)
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

INSERT INTO t_customer (id, name, last_name, company_name, tax_id_number, home_address_id, shipping_address_id, archived)
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

INSERT INTO t_address (id, street_name, building_number, flat_number, city, zip_code, voivodeship, country, archived)
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

INSERT INTO t_customer (id, name, last_name, company_name, tax_id_number, home_address_id, shipping_address_id, archived)
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

INSERT INTO t_product (id, name, description, SKU, estimated_net_unit_price, estimated_gross_unit_price, length, height, width, weight, archived)
VALUES (
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    'Product 1',
    'Description for Product 1',
    'SKU001',
    50.00,
    60.00,
    10.0,
    5.0,
    8.0,
    2.0,
    FALSE
);

INSERT INTO t_product (id, name, description, SKU, estimated_net_unit_price, estimated_gross_unit_price, length, height, width, weight, archived)
VALUES (
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981',
    'Product 2',
    'Description for Product 2',
    'SKU002',
    40.00,
    48.00,
    12.0,
    6.0,
    9.0,
    3.0,
    FALSE
);

INSERT INTO t_product (id, name, description, SKU, estimated_net_unit_price, estimated_gross_unit_price, length, height, width, weight, archived)
VALUES (
    'e8d0b44b-3e22-4fa9-abc1-6c8e1fb1b1dc',
    'Product 3',
    'Description for Product 3',
    'SKU003',
    30.00,
    36.00,
    14.0,
    7.0,
    10.0,
    4.0,
    FALSE
);

INSERT INTO t_product (id, name, description, SKU, estimated_net_unit_price, estimated_gross_unit_price, length, height, width, weight, archived)
VALUES (
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    'Product 5',
    'Description for Product 5',
    'SKU005',
    20.00,
    24.00,
    18.0,
    9.0,
    12.0,
    6.0,
    FALSE
);

-- Insert 5 orders with at least 2 order items each

-- Order 1
INSERT INTO t_order (id, order_date, customer_id, shipping_address_id, archived)
VALUES (
    '1a1a1a1a-1a1a-1a1a-1a1a-1a1a1a1a1a1a',
    '2024-01-01',
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    FALSE
);

-- Order 1 Items
INSERT INTO t_order_item (id, order_id, product_id, quantity, discount, net_price, gross_price, archived)
VALUES
(
    '11111111-1111-1111-1111-111111111111',
    '1a1a1a1a-1a1a-1a1a-1a1a-1a1a1a1a1a1a',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    2,
    0.00,
    50.00,
    60.00,
    FALSE
),
(
    '22222222-2222-2222-2222-222222222222',
    '1a1a1a1a-1a1a-1a1a-1a1a-1a1a1a1a1a1a',
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981',
    3,
    0.00,
    40.00,
    48.00,
    FALSE
);

-- Order 2
INSERT INTO t_order (id, order_date, customer_id, shipping_address_id, archived)
VALUES (
    '2b2b2b2b-2b2b-2b2b-2b2b-2b2b2b2b2b2b',
    '2024-01-02',
    '9a8b7c6d-5e4f-3a2b-1c9a-8b7c6d5e4f3a',
    '5e4f3a2b-1c9a-8b7c-6d5e-4f3a2b1c9a8b',
    FALSE
);

-- Order 2 Items
INSERT INTO t_order_item (id, order_id, product_id, quantity, discount, net_price, gross_price, archived)
VALUES
(
    '33333333-3333-3333-3333-333333333333',
    '2b2b2b2b-2b2b-2b2b-2b2b-2b2b2b2b2b2b',
    'e8d0b44b-3e22-4fa9-abc1-6c8e1fb1b1dc',
    1,
    0.00,
    30.00,
    36.00,
    FALSE
),
(
    '44444444-4444-4444-4444-444444444444',
    '2b2b2b2b-2b2b-2b2b-2b2b-2b2b2b2b2b2b',
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    4,
    0.00,
    20.00,
    24.00,
    FALSE
);

-- Create other orders in a similar fashion...
-- Order 3
INSERT INTO t_order (id, order_date, customer_id, shipping_address_id, archived)
VALUES (
    '3c3c3c3c-3c3c-3c3c-3c3c-3c3c3c3c3c3c',
    '2024-01-03',
    '9a8b7c6d-5e4f-3a2b-1c9a-8b7c6d5e4f3a',
    '5e4f3a2b-1c9a-8b7c-6d5e-4f3a2b1c9a8b',
    FALSE
);

-- Order 3 Items
INSERT INTO t_order_item (id, order_id, product_id, quantity, discount, net_price, gross_price, archived)
VALUES
(
    '55555555-5555-5555-5555-555555555555',
    '3c3c3c3c-3c3c-3c3c-3c3c-3c3c3c3c3c3c',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    1,
    0.00,
    50.00,
    60.00,
    FALSE
),
(
    '66666666-6666-6666-6666-666666666666',
    '3c3c3c3c-3c3c-3c3c-3c3c-3c3c3c3c3c3c',
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981',
    2,
    0.00,
    40.00,
    48.00,
    FALSE
);

-- Order 4
INSERT INTO t_order (id, order_date, customer_id, shipping_address_id, archived)
VALUES (
    '4d4d4d4d-4d4d-4d4d-4d4d-4d4d4d4d4d4d',
    '2024-01-04',
    '3b2a1c9a-8b7c-6d5e-4f3a-2b1c9a8b7c6d',
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    FALSE
);

-- Order 4 Items
INSERT INTO t_order_item (id, order_id, product_id, quantity, discount, net_price, gross_price, archived)
VALUES
(
    '77777777-7777-7777-7777-777777777777',
    '4d4d4d4d-4d4d-4d4d-4d4d-4d4d4d4d4d4d',
    'e8d0b44b-3e22-4fa9-abc1-6c8e1fb1b1dc',
    3,
    0.00,
    30.00,
    36.00,
    FALSE
),
(
    '88888888-8888-8888-8888-888888888888',
    '4d4d4d4d-4d4d-4d4d-4d4d-4d4d4d4d4d4d',
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    4,
    0.00,
    20.00,
    24.00,
    FALSE
);

-- Order 5
INSERT INTO t_order (id, order_date, customer_id, shipping_address_id, archived)
VALUES (
    '5e5e5e5e-5e5e-5e5e-5e5e-5e5e5e5e5e5e',
    '2024-01-05',
    'bbf9a6e4-35e1-4f6e-b18b-5c1d319a7981',
    'b5205b4f-22fd-4a95-b8c3-d8c82f524545',
    FALSE
);

-- Order 5 Items
INSERT INTO t_order_item (id, order_id, product_id, quantity, discount, net_price, gross_price, archived)
VALUES
(
    '99999999-9999-9999-9999-999999999999',
    '5e5e5e5e-5e5e-5e5e-5e5e-5e5e5e5e5e5e',
    'e8d0b44b-3e22-4fa9-abc1-6c8e1fb1b1dc',
    2,
    0.00,
    30.00,
    36.00,
    FALSE
),
(
    'aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa',
    '5e5e5e5e-5e5e-5e5e-5e5e-5e5e5e5e5e5e',
    '4d3c2b1a-9a8b-7c6d-5e4f-3a2b1c9a8b7c',
    3,
    0.00,
    20.00,
    24.00,
    FALSE
);

