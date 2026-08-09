-- ============================================
-- Migration: V3__INSERT_DATA.sql
-- ============================================

INSERT INTO catalogs (name, description, price, stock, category, created_by) VALUES
('Ceviche de Pescado', 'Ceviche de pescado fresco con limón', 15.99, 20, 'Comida', 1),
('Ceviche de Mariscos', 'Ceviche de mariscos frescos con limón', 18.99, 25, 'Comida', 1),
('Ceviche Mixto', 'Ceviche de pescado y mariscos frescos con limón', 20.99, 15, 'Comida', 1),
('Ceviche Vegano', 'Ceviche de vegetales frescos con limón', 12.99, 10, 'Comida', 1),
('Ceviche de Camarón', 'Ceviche de camarón fresco con limón', 17.99, 30, 'Comida', 1),
('Ceviche de Pulpo', 'Ceviche de pulpo fresco con limón', 19.99, 12, 'Comida', 1),
('Ceviche de Conchas Negras', 'Ceviche de conchas negras frescas con limón', 21.99, 8, 'Comida', 1);
