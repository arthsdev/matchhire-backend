-- ==============================================================
-- V3__create_application_table.sql
-- Description: Creates the applications table linking candidates and jobs.
-- ==============================================================

CREATE TABLE applications (
    id BINARY(16) NOT NULL PRIMARY KEY,
    public_id BINARY(16) NOT NULL UNIQUE,
    candidate_id BINARY(16) NOT NULL,
    job_id BINARY(16) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    active BOOLEAN DEFAULT TRUE,
    score DOUBLE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_candidate
        FOREIGN KEY (candidate_id) REFERENCES candidates(id),

    CONSTRAINT fk_application_job
        FOREIGN KEY (job_id) REFERENCES jobs(id)
);
