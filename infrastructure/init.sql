-- MyFinanceManager Database Initialization Script
-- This script runs automatically when the PostgreSQL container is first created

-- Enable UUID extension (useful for future UUID-based IDs)
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- Create enum types for better data integrity
CREATE TYPE account_type AS ENUM ('BANK', 'CREDIT_CARD', 'CRYPTO', 'INVESTMENT');
CREATE TYPE transaction_category AS ENUM (
    'SERVICES', 'FOOD', 'ENTERTAINMENT', 'RENT', 
    'TRANSPORT', 'HEALTH', 'EDUCATION', 'OTHER'
);
CREATE TYPE subscription_frequency AS ENUM ('MONTHLY', 'ANNUAL', 'WEEKLY', 'QUARTERLY');
CREATE TYPE crypto_currency AS ENUM ('BTC', 'ETH', 'NEO', 'USDT', 'USDC');

-- User table (core entity)
CREATE TABLE IF NOT EXISTS "user" (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Account table (banks, credit cards, crypto wallets)
CREATE TABLE IF NOT EXISTS account (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
    name VARCHAR(255) NOT NULL,
    type account_type NOT NULL,
    balance DECIMAL(15, 2) DEFAULT 0.00,
    currency VARCHAR(3) DEFAULT 'COP',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Subscription table (recurring payments)
CREATE TABLE IF NOT EXISTS subscription (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    provider_name VARCHAR(255) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    frequency subscription_frequency NOT NULL,
    next_billing_date DATE NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Transaction table (individual transactions)
CREATE TABLE IF NOT EXISTS transaction (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    amount DECIMAL(15, 2) NOT NULL,
    category transaction_category,
    description TEXT,
    transaction_date DATE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Crypto holding table (crypto investments)
CREATE TABLE IF NOT EXISTS crypto_holding (
    id BIGSERIAL PRIMARY KEY,
    account_id BIGINT NOT NULL REFERENCES account(id) ON DELETE CASCADE,
    currency crypto_currency NOT NULL,
    amount DECIMAL(18, 8) NOT NULL,
    average_buy_price DECIMAL(15, 2),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(account_id, currency)
);

-- Create indexes for better query performance
CREATE INDEX idx_user_email ON "user"(email);
CREATE INDEX idx_account_user_id ON account(user_id);
CREATE INDEX idx_account_type ON account(type);
CREATE INDEX idx_subscription_account_id ON subscription(account_id);
CREATE INDEX idx_subscription_next_billing ON subscription(next_billing_date);
CREATE INDEX idx_transaction_account_id ON transaction(account_id);
CREATE INDEX idx_transaction_date ON transaction(transaction_date);
CREATE INDEX idx_crypto_holding_account_id ON crypto_holding(account_id);

-- Function to update updated_at timestamp
CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = CURRENT_TIMESTAMP;
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Triggers for updated_at
CREATE TRIGGER update_user_updated_at BEFORE UPDATE ON "user" 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_account_updated_at BEFORE UPDATE ON account 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_subscription_updated_at BEFORE UPDATE ON subscription 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();
CREATE TRIGGER update_crypto_holding_updated_at BEFORE UPDATE ON crypto_holding 
    FOR EACH ROW EXECUTE FUNCTION update_updated_at_column();

-- Insert default admin user (password: admin123 - hashed with BCrypt for dev only)
-- In production, passwords should be properly hashed
INSERT INTO "user" (email, password_hash, first_name, last_name) 
VALUES ('admin@mfm.local', '$2a$10$N9qo8uLOickgx2ZMRZoMy.MqjTaQ/FVvGYVVvvVVVVVVVVVVVVV', 'Admin', 'User')
ON CONFLICT (email) DO NOTHING;