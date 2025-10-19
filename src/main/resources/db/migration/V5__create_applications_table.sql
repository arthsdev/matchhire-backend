-- ==============================================================
-- V5__create_applications_table.sql
-- Description: Creates the applications table linking candidates to jobs.
-- ==============================================================

CREATE TABLE applications (
    id BINARY(16) NOT NULL PRIMARY KEY,
    public_id BINARY(16) NOT NULL UNIQUE,
    candidate_id BINARY(16) NOT NULL,
    job_id BINARY(16) NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING',
    score DECIMAL(5,2),
    active BOOLEAN DEFAULT TRUE,
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_applications_candidate FOREIGN KEY (candidate_id) REFERENCES candidates(id),
    CONSTRAINT fk_applications_job FOREIGN KEY (job_id) REFERENCES jobs(id),
    UNIQUE (candidate_id, job_id)
);

-- Indexes to speed up queries filtering by candidate, job, and status
CREATE INDEX idx_applications_candidate_id ON applications(candidate_id);
CREATE INDEX idx_applications_job_id ON applications(job_id);
CREATE INDEX idx_applications_status ON applications(status);