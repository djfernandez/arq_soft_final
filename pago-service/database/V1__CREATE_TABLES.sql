-- ============================================
-- Migration: V1__CREATE_TABLES.sql
-- Database: Orderdb (Docker container: postgres-order)
-- ============================================

-- Función para auto-actualizar updated_at
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

CREATE TABLE IF NOT EXISTS payments (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT,
    order_id BIGINT,
    amount NUMERIC(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',--PENDING, COMPLETED, FAILED, REFUNDED
    paid_at TIMESTAMP,    
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT chk_amount_positive CHECK (amount > 0)    
);

-- Trigger para actualizar updated_at
CREATE TRIGGER update_payment_updated_at
    BEFORE UPDATE ON payments
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();

-- Comentarios
  COMMENT ON TABLE payments IS 'Catálogos del sistema - DB en Docker';
  COMMENT ON COLUMN payments.user_id IS 'Usuario creador (ref. lógica a userdb.users.id en otro contenedor)';
