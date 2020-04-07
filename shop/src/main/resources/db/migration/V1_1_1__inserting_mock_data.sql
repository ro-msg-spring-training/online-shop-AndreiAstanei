-- Inserting data into Customer Table
insert into Customer (id, email_address, first_name, last_name, password, username)
values (1, 'vpell0@bizjournals.com', 'Vincenty', 'Pell', 'HfRtxj', 'vpell0');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (2, 'mbourget1@nasa.gov', 'Marco', 'Bourget', 'u1RSSDDSg', 'mbourget1');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (3, 'kscurry2@webnode.com', 'Kilian', 'Scurry', 'idgb0mM', 'kscurry2');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (4, 'vtween3@fastcompany.com', 'Verney', 'Tween', '0GWSxJBgNsz', 'vtween3');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (5, 'tgavigan4@infoseek.co.jp', 'Tadeo', 'Gavigan', 'P7boqUn', 'tgavigan4');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (6, 'hstowte5@ebay.co.uk', 'Hermy', 'Stowte', 'cxFU7Af', 'hstowte5');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (7, 'wgurys6@youtu.be', 'Winfred', 'Gurys', 'wdEBwfLZ3F', 'wgurys6');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (8, 'lbortoluzzi7@liveinternet.ru', 'Langston', 'Bortoluzzi', 'yjNoacK5', 'lbortoluzzi7');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (9, 'testuser@mail.com', 'Testuser', 'Test', 'password', 'testuser');
insert into Customer (id, email_address, first_name, last_name, password, username)
values (10, 'johndoe@mail.com', 'John', 'Doe', 'password', 'johndoe');

-- Inserting data into Product_Category Table
insert into Product_Category (id, description, name)
values (1, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae', 'Evita');
insert into Product_Category (id, description, name)
values (2, 'In hac habitasse platea dictumst.', 'Zorah');
insert into Product_Category (id, description, name)
values (3, 'Suspendisse ornare consequat lectus. In est risus, auctor sed, tristique in, tempus sit amet, sem.',
        'Shari');
insert into Product_Category (id, description, name)
values (4, 'Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue. Vestibulum ante ipsum primis.',
        'Coretta');
insert into Product_Category (id, description, name)
values (5, 'Morbi quis tortor id nulla ultrices aliquet. Maecenas leo odio, condimentum id, luctus nec', 'Donica');
insert into Product_Category (id, description, name)
values (6, 'Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue.',
        'Liza');
insert into Product_Category (id, description, name)
values (7, 'Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;', 'Dyana');
insert into Product_Category (id, description, name)
values (8, 'Curabitur convallis.', 'Filide');
insert into Product_Category (id, description, name)
values (9, 'Integer tincidunt ante vel ipsum. Praesent blandit lacinia erat.', 'Blancha');
insert into Product_Category (id, description, name)
values (10, 'Aliquam erat volutpat. In congue.', 'Sherrie');

-- Inserting data into Supplier Table
insert into Supplier (id, name)
values (1, 'Seritage Growth Properties');
insert into Supplier (id, name)
values (2, 'WisdomTree U.S. Quality Dividend Growth Fund');
insert into Supplier (id, name)
values (3, 'Amedica Corporation');
insert into Supplier (id, name)
values (4, 'Empire State Realty Trust, Inc.');
insert into Supplier (id, name)
values (5, 'Nasdaq, Inc.');
insert into Supplier (id, name)
values (6, 'Exactech, Inc.');
insert into Supplier (id, name)
values (7, 'Kaman Corporation');
insert into Supplier (id, name)
values (8, 'Old Second Bancorp, Inc.');

-- Inserting data into Product Table
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (1, 'In est risus, auctor sed, tristique in, tempus sit amet, sem. Fusce consequat.',
        'http://dummyimage.com/250x250.png/ff4444/ffffff', 'Lotensin', 49.35, 1.8, 1, 1);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (2, 'Sed accumsan felis. Ut at dolor quis odio consequat varius.',
        'http://dummyimage.com/250x250.png/5fa2dd/ffffff', 'Protective and Moisturizing Skin', 208.38, 2.2, 2, 2);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (3, 'Nulla justo. Aliquam quis turpis eget elit sodales scelerisque.',
        'http://dummyimage.com/250x250.png/5fa2dd/ffffff', 'Aspergillus flavus', 105.74, 4.7, 3, 3);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (4, 'Nulla tempus.', 'http://dummyimage.com/250x250.png/dddddd/000000', 'Paclitaxel', 261.97, 1.7, 4, 4);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (5, 'Nam nulla. Integer pede justo, lacinia eget, tincidunt eget, tempus vel, pede.',
        'http://dummyimage.com/250x250.png/5fa2dd/ffffff', 'Fluticasone Propionate', 265.66, 4.4, 5, 5);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (6, 'Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus.',
        'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Promethazine Hydrochloride', 236.72, 4.6, 6, 6);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (7, 'Pellentesque at nulla.', 'http://dummyimage.com/250x250.png/ff4444/ffffff', 'Levetiracetam', 34.13, 4.6, 7,
        7);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (8, 'Nullam sit amet turpis elementum ligula vehicula consequat. Morbi a ipsum.',
        'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Daily Facial Moisturizer Broad Spectrum SPF15 Sunscreen',
        150.2, 1.2, 8, 8);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (9, 'Etiam pretium iaculis justo.', 'http://dummyimage.com/250x250.png/dddddd/000000', 'Clearasil Daily Clear',
        226.68, 2.8, 9, 1);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (10, 'Pellentesque ultrices mattis odio. Donec vitae nisi.', 'http://dummyimage.com/250x250.png/ff4444/ffffff',
        'nasal decongestant pe', 238.13, 4.5, 10, 2);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (11, 'Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl.',
        'http://dummyimage.com/250x250.png/ff4444/ffffff', 'Natural Cherry Honey Herb Throat Drops', 78.74, 1.0, 1, 3);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (12, 'Mauris lacinia sapien quis libero.', 'http://dummyimage.com/250x250.png/5fa2dd/ffffff', 'EGG WHITE',
        213.62, 3.3, 2, 4);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (13, 'Fusce lacus purus, aliquet at, feugiat non, pretium quis, lectus.',
        'http://dummyimage.com/250x250.png/cc0000/ffffff', 'ALSUMA', 158.11, 4.2, 3, 5);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (14, 'Nullam molestie nibh in lectus. Pellentesque at nulla.', 'http://dummyimage.com/250x250.png/ff4444/ffffff',
        'ADSOL Red Cell Preservation Solution System in Plastic Container (PL 146 Plastic)', 233.36, 3.1, 4, 6);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (15, 'Curabitur at ipsum ac tellus semper interdum.', 'http://dummyimage.com/250x250.png/dddddd/000000',
        'Pramipexole Dihydrochloride', 212.93, 1.8, 10, 4);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (16, 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit.',
        'http://dummyimage.com/250x250.png/ff4444/ffffff', 'Dual Senses Scalp Regulation AntiDandruff Shampoo', 190.89,
        5.0, 5, 7);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (17, 'Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum.',
        'http://dummyimage.com/250x250.png/5fa2dd/ffffff', 'TORSEMIDE', 103.57, 3.0, 6, 8);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (18, 'Suspendisse potenti.', 'http://dummyimage.com/250x250.png/ff4444/ffffff', 'Montelukast Sodium', 191.84,
        2.7, 7, 1);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (19, 'In sagittis dui vel nisl.', 'http://dummyimage.com/250x250.png/cc0000/ffffff', 'Cetirizine Hydrochloride',
        256.31, 3.6, 8, 2);
insert into Product (id, description, image_url, name, price, weight, category_id, supplier_id)
values (20,
        'Donec vitae nisi. Nam ultrices, libero non mattis pulvinar, nulla pede ullamcorper augue, a suscipit nulla elit ac nulla.',
        'http://dummyimage.com/250x250.png/ff4444/ffffff', 'Enalapril Maleate', 270.12, 4.9, 9, 3);

-- Inserting data into Location Table
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (1, 'Timisoara', 'RO', 'Timis', 'Piata Consiliul Europei 2', 'Iulius Mall');
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (2, 'Timisoara', 'RO', 'Timis', 'Strada Siemens 1', 'Continental');
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (3, 'Arad', 'RO', 'Arad', 'Calea Aurel Valicu 10-12', 'Atrium Mall');
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (4, 'Bucuresti', 'RO', 'Ilfov', 'Strada Izvor 2-4', 'Palatul Parlamentului');
insert into Location (id, address_city, address_country, address_county, address_street_address, name)
values (5, 'Cluj-Napoca', 'RO', 'CLuj', 'Strada Regele Ferdinand 20', 'Hotel Transilvania');

-- Inserting data into Stock Table
insert into Stock (id, quantity, location_id, product_id)
values (1, 120, 1, 1);
insert into Stock (id, quantity, location_id, product_id)
values (2, 215, 2, 2);
insert into Stock (id, quantity, location_id, product_id)
values (3, 118, 3, 3);
insert into Stock (id, quantity, location_id, product_id)
values (4, 64, 4, 4);
insert into Stock (id, quantity, location_id, product_id)
values (5, 133, 5, 5);
insert into Stock (id, quantity, location_id, product_id)
values (6, 20, 4, 6);
insert into Stock (id, quantity, location_id, product_id)
values (7, 62, 3, 7);
insert into Stock (id, quantity, location_id, product_id)
values (8, 51, 2, 8);
insert into Stock (id, quantity, location_id, product_id)
values (9, 115, 1, 9);
insert into Stock (id, quantity, location_id, product_id)
values (10, 96, 2, 10);
insert into Stock (id, quantity, location_id, product_id)
values (11, 223, 3, 11);
insert into Stock (id, quantity, location_id, product_id)
values (12, 114, 4, 12);
insert into Stock (id, quantity, location_id, product_id)
values (13, 155, 5, 13);
insert into Stock (id, quantity, location_id, product_id)
values (14, 235, 4, 14);
insert into Stock (id, quantity, location_id, product_id)
values (15, 260, 3, 15);
insert into Stock (id, quantity, location_id, product_id)
values (16, 298, 2, 16);
insert into Stock (id, quantity, location_id, product_id)
values (17, 74, 2, 17);
insert into Stock (id, quantity, location_id, product_id)
values (18, 155, 1, 18);
insert into Stock (id, quantity, location_id, product_id)
values (19, 248, 2, 19);
insert into Stock (id, quantity, location_id, product_id)
values (20, 33, 3, 20);


-- Inserting data into Revenue Table
insert into Revenue (id, date, sum, location_id)
values (1, '2020-02-03', 397.41, 1);
insert into Revenue (id, date, sum, location_id)
values (2, '2020-02-03', 413.8, 2);
insert into Revenue (id, date, sum, location_id)
values (3, '2020-02-03', 186.06, 3);
insert into Revenue (id, date, sum, location_id)
values (4, '2020-02-03', 82.61, 4);
insert into Revenue (id, date, sum, location_id)
values (5, '2020-02-03', 561.81, 5);
insert into Revenue (id, date, sum, location_id)
values (6, '2020-02-04', 223.93, 4);
insert into Revenue (id, date, sum, location_id)
values (7, '2020-02-04', 398.71, 3);
insert into Revenue (id, date, sum, location_id)
values (8, '2020-02-04', 215.8, 2);
insert into Revenue (id, date, sum, location_id)
values (9, '2020-02-04', 547.99, 1);
insert into Revenue (id, date, sum, location_id)
values (10, '2020-03-18', 180.03, 3);

-- Inserting data into Order_ Table
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (1, 'Kuala Lumpur', 'Malaysia', 'MY', '4 Graedel Court', '2020-02-03 00:00:00', 1);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (2, 'Araci', 'Brazil', 'BR', '3219 Victoria Circle', '2020-02-03 00:00:00', 2);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (3, 'Ciheras', 'Indonesia', 'ID', '3897 Fieldstone Circle', '2020-02-03 00:00:00', 3);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (4, 'Sahagún', 'Colombia', 'CO', '99007 Nancy Center', '2020-02-03 00:00:00', 4);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (5, 'Rače', 'Slovenia', 'SI', '3052 Menomonie Plaza', '2020-02-03 00:00:00', 5);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (6, 'Sierpc', 'Poland', 'PL', '5 Reindahl Lane', '2020-02-03 00:00:00', 6);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (7, 'Quiruvilca', 'Peru', 'PE', '525 Grayhawk Point', '2020-02-03 00:00:00', 7);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (8, 'El Corozo', 'Venezuela', 'VE', '5 Rigney Plaza', '2020-02-03 00:00:00', 8);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (9, 'Daqiao', 'China', 'CN', '3 Dorton Alley', '2020-02-03 00:00:00', 9);
insert into Order_ (id, address_city, address_country, address_county, address_street_address, created_at, customer_id)
values (10, 'Porto Real', 'Brazil', 'BR', '3263 Pearson Avenue', '2020-02-03 00:00:00', 10);

-- Inserting data into Order_Detail Table
insert into Order_Detail (id, quantity, order_id, product_id)
values (1, 35, 1, 1);
insert into Order_Detail (id, quantity, order_id, product_id)
values (2, 58, 2, 2);
insert into Order_Detail (id, quantity, order_id, product_id)
values (3, 80, 3, 3);
insert into Order_Detail (id, quantity, order_id, product_id)
values (4, 51, 4, 7);
insert into Order_Detail (id, quantity, order_id, product_id)
values (5, 51, 5, 8);
insert into Order_Detail (id, quantity, order_id, product_id)
values (6, 54, 6, 9);
insert into Order_Detail (id, quantity, order_id, product_id)
values (7, 6, 7, 15);
insert into Order_Detail (id, quantity, order_id, product_id)
values (8, 74, 8, 16);
insert into Order_Detail (id, quantity, order_id, product_id)
values (9, 100, 9, 17);
insert into Order_Detail (id, quantity, order_id, product_id)
values (10, 72, 10, 20);