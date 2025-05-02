CREATE TABLE IF NOT EXISTS Ingredient(
    id varchar(4) primary key,
    name varchar(20) not null,
    type varchar(10) not null
);

CREATE TABLE IF NOT EXISTS Burger_Order(
    id serial primary key,
    name varchar(20) not null,
    city varchar(20) not null,
    street varchar(40) not null,
    house varchar(10) not null,
    card_number varchar(16) not null,
    expire_date varchar(6) not null,
    card_cvv varchar(3) not null,
    placed_at timestamp not null
);

CREATE TABLE IF NOT EXISTS Burger(
    id serial primary key,
    name varchar(20) not null,
    burger_order_key integer not null,
    ingredients varchar(4) ARRAY not null,
    created_at timestamp not null,
    CONSTRAINT fk_burger_order
        FOREIGN KEY(burger_order_key)
            REFERENCES Burger_Order(id)
);
