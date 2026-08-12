-- ============================================
-- Migration: V2__ADD_INDEXES.sql
-- ============================================

CREATE INDEX idx_payment_user_id ON payments(user_id);
CREATE INDEX idx_payment_status ON payments(status);
  
CREATE INDEX idx_payment_paid_at ON payments(paid_at);
