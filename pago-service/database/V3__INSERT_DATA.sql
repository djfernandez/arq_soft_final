-- Datos de prueba
INSERT INTO payments (user_id, order_id, amount, status, paid_at) VALUES
(1, 1, 149.97, 'COMPLETED', CURRENT_TIMESTAMP),
(2, 2, 149.98, 'PENDING', NULL),
(1, 3, 89.99, 'REFUNDED', NULL);
