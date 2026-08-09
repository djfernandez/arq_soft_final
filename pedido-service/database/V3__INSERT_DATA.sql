-- Datos de prueba
INSERT INTO orders (order_number, user_id, status, total_amount) VALUES
('ORD-2025-001', 1, 'CONFIRMED', 2849.97),
('ORD-2025-002', 2, 'PENDING', 1199.98),
('ORD-2025-003', 1, 'SHIPPED', 149.99);

INSERT INTO order_items (order_id, catalog_id, quantity, unit_price, subtotal) VALUES
(1, 1, 1, 39.99, 39.99),
(1, 2, 1, 49.99, 49.99),
(1, 3, 1, 59.99, 59.99),
(2, 4, 1, 69.99, 69.99),
(2, 5, 1, 79.00, 79.00),
(3, 7, 1, 89.99, 89.99);