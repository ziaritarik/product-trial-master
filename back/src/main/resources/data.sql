INSERT INTO users (username, firstname, email, password) VALUES
('john_doe', 'John', 'john@example.com', 'password123'),
('jane_doe', 'Jane', 'jane@example.com', 'password456');

INSERT INTO products (code, name, description, image, category, price, quantity, internal_reference, shell_id, inventory_status, rating, created_at, updated_at) VALUES
('P001', 'Product 1', 'Description for Product 1', 'image1.jpg', 'Category 1', 19.99, 100, 'IR001', 'SH001', 'IN_STOCK', 4.5, NOW(), NOW()),
('P002', 'Product 2', 'Description for Product 2', 'image2.jpg', 'Category 2', 29.99, 50, 'IR002', 'SH002', 'IN_STOCK', 4.0, NOW(), NOW()),
('P003', 'Product 3', 'Description for Product 3', 'image3.jpg', 'Category 1', 39.99, 20, 'IR003', 'SH003', 'OUT_OF_STOCK', 3.5, NOW(), NOW());

INSERT INTO carts (user_id) VALUES
(1),
(2);

INSERT INTO wishlists (user_id) VALUES
(1),
(2);