-- ============================================
-- Migration: V3__INSERT_DATA.sql
-- ============================================

INSERT INTO deliveries (user_id, order_id, payment_id, status, delivered_at) VALUES
(1, 1, 1, 'DELIVERED', CURRENT_TIMESTAMP),
(2, 2, 2, 'DELIVERED', CURRENT_TIMESTAMP),
(3, 3, 3, 'DELIVERED', CURRENT_TIMESTAMP),
(4, 4, 4, 'DELIVERED', CURRENT_TIMESTAMP),
(5, 5, 5, 'DELIVERED', CURRENT_TIMESTAMP);