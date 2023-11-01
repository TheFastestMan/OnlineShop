
-- Table for users
CREATE TABLE users (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       username VARCHAR(100) UNIQUE NOT NULL,
                       password VARCHAR(60) NOT NULL,
                       email VARCHAR(100) UNIQUE NOT NULL,
                       role VARCHAR(10) NOT NULL,
                       gender VARCHAR(10) NOT NULL

);

-- Table for products
CREATE TABLE products (
                          id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                          name VARCHAR(255) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          quantity INTEGER NOT NULL
);

-- Table for reviews
CREATE TABLE reviews (
                         id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                         product_id BIGINT REFERENCES products(id),
                         reviewText VARCHAR NOT NULL,
                         rating INTEGER CHECK (rating >= 1 AND rating <= 5) NOT NULL
);

-- Table for orders
CREATE TABLE orders (
                        id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                        orderDate TIMESTAMP NOT NULL,
                        orderStatus varchar(20) NOT NULL
);

-- Table for order_details
CREATE TABLE order_details (
                               id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                               order_id BIGINT REFERENCES orders(id),
                               product_id BIGINT REFERENCES products(id),
                               quantity INTEGER NOT NULL
);

-- Table for carts
CREATE TABLE carts (
                       id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                       user_id BIGINT REFERENCES users(id),
                       createdAt TIMESTAMP NOT NULL
);

-- Table for cart_items
CREATE TABLE cart_items (
                            id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
                            cart_id BIGINT REFERENCES carts(id),
                            product_id BIGINT REFERENCES products(id),
                            quantity INTEGER NOT NULL
);


-- just