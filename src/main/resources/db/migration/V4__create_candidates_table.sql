-- ==============================================================
-- V4__create_candidates_table.sql
-- Description: Creates the candidates table.
-- ==============================================================

CREATE TABLE candidates (
    id BINARY(16) NOT NULL PRIMARY KEY,
    public_id BINARY(16) NOT NULL UNIQUE,
    name VARCHAR(150) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone VARCHAR(20),
    resume_url VARCHAR(255),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Index on email for fast lookup and uniqueness enforcement
CREATE INDEX idx_candidates_email ON candidates(email);