-- Добавление тестовых товаров
INSERT INTO Products (name, description, price, quantity)
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

-- Добавление тестового заказа
INSERT INTO Orders (order_date, status)
VALUES (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен'),
       (current_date, 'оформлен');

-- Добавление детали для тестового заказа
INSERT INTO OrderDetails (order_id, product_id, quantity)
VALUES (1, 1, 1),
       (2, 2, 1),
       (3, 3, 1),
       (4, 4, 1),
       (5, 5, 1),
       (6, 6, 1),
       (7, 7, 1),
       (8, 8, 1),
       (9, 9, 1),
       (10, 10, 1);

-- Добавление тестового отзыва
INSERT INTO Reviews (product_id, review_text, rating)
VALUES (2, 'Отличный смартфон!', 5),
       (3, 'Пылесос супер!', 5),
       (6, 'Самые лучшие наушники!', 5),
       (6, 'Лучшие наушники которые когда либо слышал!', 5),
       (6, 'Отличный звук!', 5),
       (6, 'Хорошее качество!', 5),
       (7, 'Не плохие наушники за свои деньги', 5),
       (7, 'Хорошие наушники!', 5),
       (7, 'Пришли в мятой упаковке!', 4),
       (7, '"Все отлично, то что нужно!"', 5),
       (4, 'Лучший!', 5),
       (4, 'Супер!', 5),
       (4, 'Очень понравилось', 5),
       (10, 'Хорошее изображение ', 5),
       (10, 'Не оправдал ожидания', 3),
       (10, 'Пришел с разбитым экраном', 1),
       (10, 'Не хватает деталей', 2),
       (10, 'Лучшее на рынке за свои деньги', 5),
       (1, 'Отличный!', 5),
       (2, '"Хороший, но дорогой;)"', 5),
       (5, 'Отличный телефон!', 5),
       (8, 'Хорошее качество!', 5),
       (8, '"Не оправдал ожидания, тяжелый!"', 4),
       (9, 'Отличный!', 5);
-- Добавление тестового Users
INSERT INTO users (username, password, email)
VALUES ('user1', 'password1', 'user1@example.com'),
       ('user2', 'password2', 'user2@example.com'),
       ('user3', 'password3', 'user3@example.com');
-- Добавление тестового Carts
INSERT INTO carts (user_id)
VALUES (1),
       (2),
       (3);
-- Добавление тестового Cart Items
INSERT INTO cart_items (cart_id, product_id, quantity)
VALUES (1, 1, 2),
       (1, 3, 1),
       (2, 2, 3),
       (2, 4, 2),
       (3, 5, 1),
       (3, 6, 2);
