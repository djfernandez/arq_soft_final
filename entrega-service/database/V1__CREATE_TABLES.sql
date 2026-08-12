    -- ============================================
    -- Migration: V1__CREATE_TABLES.sql
    -- Database: Catalogdb (Docker container: postgres-catalog)
    -- ============================================

    -- Función para auto-actualizar updated_at
    CREATE OR REPLACE FUNCTION update_updated_at_column()
    RETURNS TRIGGER AS $$
    BEGIN
        NEW.updated_at = CURRENT_TIMESTAMP;
        RETURN NEW;
    END;
    $$ language 'plpgsql';

    -- Tabla de catálogos
    CREATE TABLE IF NOT EXISTS deliveries (
        id BIGSERIAL PRIMARY KEY,
        user_id BIGINT NOT NULL,
        order_id BIGINT NOT NULL,
        payment_id BIGINT NOT NULL,
        delivery_id BIGINT,
        status VARCHAR(20) NOT NULL DEFAULT 'PENDING',        
        delivered_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,        
        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

        CONSTRAINT chk_user_id_positive CHECK (user_id > 0),
        CONSTRAINT chk_order_id_positive CHECK (order_id > 0),
        CONSTRAINT chk_payment_id_positive CHECK (payment_id > 0)
    );

    -- Trigger para actualizar updated_at
    CREATE TRIGGER update_deliveries_updated_at
        BEFORE UPDATE ON deliveries
        FOR EACH ROW
        EXECUTE FUNCTION update_updated_at_column();

    -- Comentarios
    COMMENT ON TABLE deliveries IS 'Entregas del sistema - DB en Docker';
    COMMENT ON COLUMN deliveries.user_id IS 'ID del usuario (ref. lógica a userdb.users.id en otro contenedor)';
