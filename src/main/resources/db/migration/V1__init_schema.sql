
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

CREATE TYPE consent_status AS ENUM ('pending', 'approved', 'rejected');

-- INDEPENDENT TABLES

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    user_name TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE roles(
    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    role TEXT NOT NULL UNIQUE
);

CREATE TABLE consent_forms(
    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,
    id_card_file_path TEXT NOT NULL,
    id_face_file_path TEXT NOT NULL,
    face_fff_file_path TEXT NOT NULL,
    approve_rules BOOLEAN NOT NULL
);

-- DEPENDENT TABLES (FK)

 CREATE TABLE photos(
    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

    uploaded_by_user_id BIGINT NOT NULL,
    photo_file_path TEXT NOT NULL,

    size_bytes BIGINT NOT NULL,
    mime_type TEXT NOT NULL,
    file_name TEXT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    CONSTRAINT fk_photos_uploaded_by_user
    FOREIGN KEY (uploaded_by_user_id)
    REFERENCES users(id)
    ON DELETE CASCADE
)

-- INDEX

CREATE INDEX idx_photos_uploaded_by_user_id
ON photos(uploaded_by_user_id);

-- JUNCTION TABLES

CREATE TABLE users_roles(
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles(id) ON DELETE CASCADE
);

CREATE TABLE users_consent_forms(
    user_id BIGINT NOT NULL,
    consent_form_id BIGINT NOT NULL,
    status consent_status NOT NULL,
    PRIMARY KEY(user_id, consent_form_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (consent_form_id) REFERENCES consent_forms(id) ON DELETE CASCADE
);


