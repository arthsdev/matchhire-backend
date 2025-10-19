-- ==============================================================
-- V1__create_companies_table.sql
-- Description: Creates the companies table.
-- ==============================================================

CREATE TABLE companies (
    id BINARY(16) NOT NULL PRIMARY KEY,
    public_id BINARY(16) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Index on email to speed up search queries and ensure uniqueness.
CREATE INDEX idx_companies_email ON companies(email);
