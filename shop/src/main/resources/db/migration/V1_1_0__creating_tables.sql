drop table if exists stock;
drop table if exists order_detail;
drop table if exists order_;
drop table if exists revenue;
drop table if exists location;
drop table if exists customer;
drop table if exists product;
drop table if exists product_category;
drop table if exists supplier;
drop table if exists hibernate_sequence;
drop table if exists order_locations;
drop table if exists shopping_cart_item;
drop table if exists shopping_cart;


create table customer
(
    id               integer not null auto_increment,
    email_address    varchar(255),
    first_name       varchar(255),
    last_name        varchar(255),
    password         varchar(255),
    username         varchar(255),
    shopping_cart_id integer,
    primary key (id)
);

create table location
(
    id                     integer not null auto_increment,
    address_city           varchar(255),
    address_country        varchar(255),
    address_county         varchar(255),
    address_street_address varchar(255),
    name                   varchar(255),
    primary key (id)
);

create table order_
(
    id                     integer not null auto_increment,
    created_at             datetime,
    address_city           varchar(40),
    address_country        varchar(40),
    address_county         varchar(40),
    address_street_address varchar(160),
    order_timestamp        integer,
    customer_id            integer,
    primary key (id)
);

create table order_locations
(
    order_id    integer not null,
    location_id integer not null,
    primary key (order_id, location_id)
);

create table order_detail
(
    id         integer not null auto_increment,
    quantity   integer,
    order_id   integer,
    product_id integer,
    primary key (id)
);

create table product
(
    id          integer          not null auto_increment,
    description varchar(255),
    image_url   varchar(255),
    name        varchar(255),
    price       decimal(8, 2),
    weight      double precision not null,
    category_id integer,
    supplier_id integer,
    primary key (id)
);

create table product_category
(
    id          integer not null auto_increment,
    description varchar(255),
    name        varchar(50) UNIQUE,
    primary key (id)
);

create table revenue
(
    id          integer not null auto_increment,
    date        date,
    sum         decimal(9, 2),
    location_id integer,
    primary key (id)
);

create table shopping_cart
(
    id integer not null auto_increment,
    primary key (id)
);

create table shopping_cart_item
(
    id               integer not null auto_increment,
    quantity         integer,
    product_id       integer,
    shopping_cart_id integer,
    primary key (id)
);

create table stock
(
    id          integer not null auto_increment,
    quantity    integer,
    location_id integer,
    product_id  integer,
    primary key (id)
);

create table supplier
(
    id   integer not null auto_increment,
    name varchar(255) UNIQUE,
    primary key (id)
);

alter table customer
    add constraint FK_ShoppingCart_To_Customer foreign key (shopping_cart_id) references shopping_cart (id);
alter table order_
    add constraint FK_Customer_To_Order foreign key (customer_id) references customer (id);
alter table order_locations
    add constraint FK_OrderLocations_To_Location foreign key (location_id) references location (id);
alter table order_locations
    add constraint FK_OrderLocations_To_Order foreign key (order_id) references order_ (id);
alter table order_detail
    add constraint FK_Order_To_OrderDetail foreign key (order_id) references order_ (id);
alter table order_detail
    add constraint FK_Product_To_OrderDetail foreign key (product_id) references product (id);
alter table product
    add constraint FK_ProductCategory_To_Product foreign key (category_id) references product_category (id);
alter table product
    add constraint FK_Supplier_To_Product foreign key (supplier_id) references supplier (id);
alter table product
    add constraint Product_Unique_Fields UNIQUE (name, category_id, supplier_id);
alter table revenue
    add constraint FK_Location_To_Revenue foreign key (location_id) references location (id);
alter table shopping_cart_item
    add constraint FK_ShoppingCartItem_To_Product foreign key (product_id) references product (id);
alter table shopping_cart_item
    add constraint FK_ShoppingCartItem_To_ShoppingCart foreign key (shopping_cart_id) references shopping_cart (id);
alter table stock
    add constraint FK_Location_To_Stock foreign key (location_id) references location (id);
alter table stock
    add constraint FK_Product_To_Stock foreign key (product_id) references product (id) ON DELETE CASCADE;