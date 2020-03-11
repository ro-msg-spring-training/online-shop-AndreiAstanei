-- Inserting data into Customer Table
insert into Customer (id, email_address, first_name, last_name, password, username)
values (1, 'testuser@mail.com', 'Testuser', 'Test', 'password', 'testuser');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (2, 'johndoe@mail.com', 'John', 'Doe', 'password', 'johndoe');

-- Inserting data into Product_Category Table
insert into Product_Category (id, description, name)
values (1, 'Description for Laptops Category', 'Evita');
insert into Product_Category (id, description, name)
values (2, 'Description for Printers Category', 'Zorah');
insert into Product_Category (id, description, name)
values (3, 'Description for Fridges Category', 'Shari');

-- Inserting data into Supplier Table
insert into Supplier (id, name)
values (1, 'Supplier 1');
insert into Supplier (id, name)
values (2, 'Supplier 2');

-- Inserting data into Product Table
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (1, 'Description for Product 1', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Product 1', 49.35, 1.8, 1,
        1);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (2, 'Description for Product 2', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Product 2', 208.38, 2.2, 2,
        2);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (3, 'Description for Product 3', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Product 3', 105.74, 4.7, 3,
        1);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (4, 'Description for Product 4', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Product 4', 261.97, 1.7, 1,
        2);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (5, 'Description for Product 5', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Product 5', 265.66, 4.4, 2,
        1);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (6, 'Description for Product 6', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Product 6', 236.72, 4.6, 3,
        2);

-- Inserting data into Location Table
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (1, 'City 1', 'Country 1', 'CT 1', 'Address 1', 'Location 1');
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (2, 'City 2', 'Country 2', 'CT 2', 'Address 2', 'Location 2');
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (3, 'City 3', 'Country 3', 'CT 3', 'Address 3', 'Location 3');

-- Inserting data into Stock Table
insert into Stock (id, quantity, location_id, product_id)
values (1, 10, 1, 1);
insert into Stock (id, quantity, location_id, product_id)
values (2, 15, 1, 2);
insert into Stock (id, quantity, location_id, product_id)
values (3, 10, 1, 3);
insert into Stock (id, quantity, location_id, product_id)
values (4, 10, 1, 4);
insert into Stock (id, quantity, location_id, product_id)
values (5, 10, 1, 5);
insert into Stock (id, quantity, location_id, product_id)
values (6, 10, 1, 6);
insert into Stock (id, quantity, location_id, product_id)
values (7, 15, 2, 1);
insert into Stock (id, quantity, location_id, product_id)
values (8, 10, 2, 2);
insert into Stock (id, quantity, location_id, product_id)
values (9, 10, 2, 3);
insert into Stock (id, quantity, location_id, product_id)
values (10, 10, 2, 4);
insert into Stock (id, quantity, location_id, product_id)
values (11, 10, 2, 5);
insert into Stock (id, quantity, location_id, product_id)
values (12, 10, 2, 6);
insert into Stock (id, quantity, location_id, product_id)
values (13, 10, 3, 1);
insert into Stock (id, quantity, location_id, product_id)
values (14, 10, 3, 2);

-- Inserting data into Revenue Table
insert into Revenue (id, date, sum, location_id)
values (1, '2020-02-03', 300.00, 1);
insert into Revenue (id, date, sum, location_id)
values (2, '2020-02-03', 400.00, 2);
insert into Revenue (id, date, sum, location_id)
values (3, '2020-02-03', 200.00, 3);

-- Inserting data into Order_ Table
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id,
                    order_timestamp)
values (1, 'Order 1 City', 'Order 1 Country', 'Order 1 County', 'Order 1 Address', '2020-02-03 00:00:00', 1, -120);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id,
                    order_timestamp)
values (2, 'Order 2 City', 'Order 2 Country', 'Order 2 County', 'Order 2 Address', '2020-02-03 00:00:00', 2, -120);

-- Inserting data into Order_Detail Table
insert into Order_Detail (id, quantity, order_id, product_id)
values (1, 1, 1, 1);
insert into Order_Detail (id, quantity, order_id, product_id)
values (2, 1, 1, 2);
insert into Order_Detail (id, quantity, order_id, product_id)
values (3, 1, 2, 1);
insert into Order_Detail (id, quantity, order_id, product_id)
values (4, 1, 2, 2);