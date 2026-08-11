-- Datos de prueba
INSERT INTO restaurants (name, description, created_by) VALUES
('Restaurante A', 'Descripción del restaurante A', 1),
('Restaurante B', 'Descripción del restaurante B', 2),
('Restaurante C', 'Descripción del restaurante C', 1);

INSERT INTO orders (order_number, user_id, status, total_amount, restaurant_id) VALUES
('ORD-2025-001', 1, 'CONFIRMED', 149.97, 1),
('ORD-2025-002', 2, 'PENDING', 149.98, 2),
('ORD-2025-003', 1, 'SHIPPED', 89.99, 3);

INSERT INTO order_items (order_id, catalog_id, quantity, unit_price, subtotal) VALUES
(1, 1, 1, 39.99, 39.99),
(1, 2, 1, 49.99, 49.99),
(1, 3, 1, 59.99, 59.99),
(2, 4, 1, 69.99, 69.99),
(2, 5, 1, 79.00, 79.00),
(3, 7, 1, 89.99, 89.99);