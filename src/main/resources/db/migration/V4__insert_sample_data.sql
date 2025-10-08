-- ==============================================================
-- V4__insert_sample_data.sql
-- Description: Inserts sample data for local development and testing.
-- ==============================================================

-- === Insert sample candidate ===
INSERT INTO candidates (
    id, public_id, name, email, phone, active
) VALUES (
    UUID_TO_BIN('a1e4d3f0-1234-4a5b-b789-ffb2a1a3c001'),
    UUID_TO_BIN('c5fbeab2-5678-43af-9d13-c2a88c1b7b02'),
    'Fabiano Augusto',
    'fabiano.augusto@example.com',
    '+55 11 99999-9999',
    TRUE
);

-- === Insert sample job ===
INSERT INTO jobs (
    id, public_id, title, description, location, salary, active
) VALUES (
    UUID_TO_BIN('b7c8e9f1-9876-45ac-82d0-d5a77b1e8c03'),
    UUID_TO_BIN('e3f9adcd-3456-4b8f-bfc7-8d1ac7c5a004'),
    'Java Developer Intern',
    'Assist in the development and maintenance of backend applications using Spring Boot.',
    'Itajubá - MG',
    2500.00,
    TRUE
);

-- === Insert sample application ===
INSERT INTO applications (
    id, public_id, candidate_id, job_id, status, active, score
) VALUES (
    UUID_TO_BIN('f6a4e1b2-7890-41f9-a32b-ff23a9e8a005'),
    UUID_TO_BIN('ab82b2aa-2345-45b0-bc9e-f0f9e3e7c006'),
    UUID_TO_BIN('a1e4d3f0-1234-4a5b-b789-ffb2a1a3c001'), -- candidate_id
    UUID_TO_BIN('b7c8e9f1-9876-45ac-82d0-d5a77b1e8c03'), -- job_id
    'PENDING',
    TRUE,
    85.5
);
