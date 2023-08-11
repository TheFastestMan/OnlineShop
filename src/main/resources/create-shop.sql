-- Таблица товаров
CREATE TABLE Products
(
    id          serial primary key,
    name        varchar(255)   NOT NULL,
    description varchar(255)   NOT NULL,
    price       decimal(10, 2) NOT NULL,
    quantity    int            NOT NULL
);

-- Таблица заказов
CREATE TABLE Orders
(
    id         serial primary key,
    order_date timestamp   NOT NULL,
    status     varchar(50) NOT NULL
);

-- Таблица детали заказов
CREATE TABLE OrderDetails
(
    id         serial primary key,
    order_id   int REFERENCES Orders (id),
    product_id int REFERENCES Products (id),
    quantity   int NOT NULL
);

-- Таблица отзывов
CREATE TABLE Reviews
(
    id          serial PRIMARY KEY,
    product_id  int REFERENCES Products (id),
    review_text varchar NOT NULL,
    rating      int CHECK (rating >= 1 AND rating <= 5)
);
