-- ============================================
-- Migration: V2__ADD_INDEXES.sql
-- ============================================

CREATE INDEX IF NOT EXISTS idx_catalogs_category ON catalogs(category);
CREATE INDEX IF NOT EXISTS idx_catalogs_created_by ON catalogs(created_by);
CREATE INDEX IF NOT EXISTS idx_catalogs_price ON catalogs(price);
CREATE INDEX IF NOT EXISTS idx_catalogs_stock ON catalogs(stock);
CREATE INDEX IF NOT EXISTS idx_catalogs_created_at ON catalogs(created_at DESC);
