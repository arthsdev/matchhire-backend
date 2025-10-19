-- ==============================================================
-- V2__create_users_table.sql
-- Description: Creates the users table and links it to companies.
-- ==============================================================

CREATE TABLE users (
    id BINARY(16) NOT NULL PRIMARY KEY,
    public_id BINARY(16) NOT NULL UNIQUE,
    company_id BINARY(16),
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_users_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- Indexes for login and role for faster lookups.
CREATE INDEX idx_users_login ON users(login);
CREATE INDEX idx_users_role ON users(role);
