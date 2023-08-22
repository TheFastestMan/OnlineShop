-- Таблица user
CREATE TABLE users
(
    id       serial PRIMARY KEY,
    username varchar(50) UNIQUE  NOT NULL,
    password varchar(100)        NOT NULL,
    email    varchar(100) UNIQUE NOT NULL

);
-- Таблица товаров
CREATE TABLE products
(
    id          serial primary key,
    name        varchar(255)   NOT NULL,
    description varchar(255)   NOT NULL,
    price       decimal(10, 2) NOT NULL,
    quantity    int            NOT NULL
);

-- Таблица заказов
CREATE TABLE orders
(
    id         serial primary key,
    order_date timestamp   NOT NULL,
    status     varchar(50) NOT NULL
);

-- Таблица детали заказов
CREATE TABLE order_details
(
    id         serial primary key,
    order_id   int REFERENCES Orders (id),
    product_id int REFERENCES Products (id),
    quantity   int NOT NULL
);

-- Таблица отзывов
CREATE TABLE reviews
(
    id          serial PRIMARY KEY,
    product_id  int REFERENCES Products (id),
    review_text varchar NOT NULL,
    rating      int CHECK (rating >= 1 AND rating <= 5)
);
-- Таблица carts
CREATE TABLE carts
(
    id         serial PRIMARY KEY,
    user_id    int REFERENCES users (id),
    created_at timestamp NOT NULL DEFAULT current_timestamp
);

-- Таблица cart_items
CREATE TABLE cart_items
(
    id         serial PRIMARY KEY,
    cart_id    int REFERENCES carts (id),
    product_id int REFERENCES products (id),
    quantity   int NOT NULL
);



