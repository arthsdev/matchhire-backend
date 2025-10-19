-- ==============================================================
-- V3__create_jobs_table.sql
-- Description: Creates the jobs table linked to companies.
-- ==============================================================

CREATE TABLE jobs (
    id BINARY(16) NOT NULL PRIMARY KEY,
    public_id BINARY(16) NOT NULL UNIQUE,
    company_id BINARY(16) NOT NULL,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    location VARCHAR(100),
    salary DECIMAL(10,2),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_jobs_company FOREIGN KEY (company_id) REFERENCES companies(id)
);

-- Indexes to improve search queries by company and title
CREATE INDEX idx_jobs_company_id ON jobs(company_id);
CREATE INDEX idx_jobs_title ON jobs(title);