-- Добавление тестовых товаров
INSERT INTO products (name, description, price, quantity)
VALUES ('Ноутбук Acer', 'Описание товара', 60000.00, 88),
       ('Смартфон Apple', 'Описание товара', 50000.00, 120),
       ('Пылесос Dyson', 'Описание товара', 60000.00, 30),
       ('Ноутбук Apple', 'Описание товара', 150000.00, 60),
       ('Смартфон Sony', 'Описание товара', 1000.00, 5),
       ('Наушники Sony', 'Описание товара', 20000.00, 10),
       ('Наушники Samsung', 'Описание товара', 5000.00, 24),
       ('Наушники Apple', 'Описание товара', 50000.00, 2),
       ('Power bank TFN', 'Описание товара', 5000.00, 200),
       ('Монитор Acer', 'Описание товара', 40000.00, 10);

-- Insert test data into users
INSERT INTO users (username, password, email, role, gender)
VALUES
    ('admin1', 'password1', 'admin1@example.com', 'ADMIN', 'MALE'),
    ('user1', 'password1', 'user1@example.com', 'USER', 'FEMALE'),
    ('user2', 'password2', 'user2@example.com', 'USER', 'MALE'),
    ('admin2', 'password2', 'admin2@example.com', 'ADMIN', 'FEMALE'),
    ('user3', 'password3', 'user3@example.com', 'USER', 'FEMALE'),
    ('admin3', 'password3', 'admin3@example.com', 'ADMIN', 'MALE'),
    ('user4', 'password4', 'user4@example.com', 'USER', 'FEMALE'),
    ('user5', 'password5', 'user5@example.com', 'USER', 'MALE'),
    ('admin4', 'password4', 'admin4@example.com', 'ADMIN', 'FEMALE'),
    ('user6', 'password6', 'user6@example.com', 'USER', 'MALE');

-- Insert test data into orders
INSERT INTO orders (orderDate, orderStatus)
VALUES
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED'),
    (current_timestamp, 'CREATED');

-- Insert test data into order_details
INSERT INTO order_details (order_id, product_id, quantity)
VALUES
    (1, 1, 1),
    (2, 2, 1),
    (3, 3, 1),
    (4, 4, 1),
    (5, 5, 1),
    (6, 6, 1),
    (7, 7, 1),
    (8, 8, 1),
    (9, 9, 1),
    (10, 10, 1);
///////
-- Insert test data into reviews
INSERT INTO reviews (product_id, reviewText, rating)
VALUES
    (2, 'Отличный смартфон!', 5),
    (3, 'Пылесос супер!', 5),
    (6, 'Самые лучшие наушники!', 5),
    (6, 'Лучшие наушники которые когда либо слышал!', 5),
    ...
    (8, 'Отличный!', 5);

-- Insert test data into carts
INSERT INTO carts (user_id, createdAt)
VALUES
    (1, current_timestamp),
    (2, current_timestamp),
    (3, current_timestamp);

-- Insert test data into cart_items
INSERT INTO cart_items (cart_id, product_id, quantity)
VALUES
    (1, 1, 2),
    (1, 3, 1),
    (2, 2, 3),
    (2, 4, 2),
    (3, 5, 1),
    (3, 6, 2);