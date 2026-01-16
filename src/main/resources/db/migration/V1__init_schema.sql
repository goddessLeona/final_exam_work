
CREATE EXTENSION IF NOT EXISTS "pgcrypto";
CREATE EXTENSION IF NOT EXISTS citext;


-- ENUM

CREATE TYPE consent_status AS ENUM ('pending', 'approved', 'rejected');
CREATE TYPE content_status AS ENUM ('published', 'draft');
CREATE TYPE album_role AS ENUM ('owner', 'editor', 'viewer');

-- TOTAL AMOUNT OF TABLES 13

-- INDEPENDENT TABLES 4
-- (users , roles , consent_forms, tags)

CREATE TABLE users(
    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

    user_name TEXT NOT NULL UNIQUE,
    password_hash TEXT NOT NULL,
    email CITEXT NOT NULL UNIQUE,
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

CREATE TABLE tags(
    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

    name_tag CITEXT NOT NULL UNIQUE
);

-- DEPENDENT TABLES (FK) 2
-- (photos, photo_albums)

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
);

CREATE TABLE photo_albums(

    id BIGSERIAL PRIMARY KEY,
    public_uuid UUID NOT NULL DEFAULT gen_random_uuid() UNIQUE,

    owner_user_id BIGINT NOT NULL,
    photo_album_name TEXT NOT NULL,

    created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
    published_at TIMESTAMPTZ,
    content_status content_status NOT NULL DEFAULT 'draft',
    rules_check BOOLEAN NOT NULL,

    CONSTRAINT fk_photo_album_owner_user
    FOREIGN KEY (owner_user_id)
    REFERENCES users(id)
    ON DELETE CASCADE,

    CONSTRAINT uq_album_owner_name
        UNIQUE (owner_user_id, photo_album_name)
);

-- JUNCTION TABLES 7
-- (users_roles, users_consent_forms, users_photo_albums, photo_person_tags, photo_contributors)
-- (photo_albums_tags, photo_albums_photos)

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
    consent_status consent_status NOT NULL,
    PRIMARY KEY (user_id, consent_form_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (consent_form_id) REFERENCES consent_forms(id) ON DELETE CASCADE
);

CREATE TABLE users_photo_albums(
    user_id BIGINT NOT NULL,
    photo_album_id BIGINT NOT NULL,
    album_role album_role NOT NULL,
    PRIMARY KEY (user_id, photo_album_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (photo_album_id) REFERENCES photo_albums(id) ON DELETE CASCADE
);

CREATE TABLE photo_person_tags(
    user_id BIGINT NOT NULL,
    photo_id BIGINT NOT NULL,

    can_remove BOOLEAN DEFAULT true,
    tagged_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    tagged_by_user_id BIGINT NOT NULL,

    PRIMARY KEY (user_id, photo_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE,

    FOREIGN KEY (tagged_by_user_id) REFERENCES users(id) ON DELETE CASCADE
);

    CREATE TABLE photo_contributors (
    user_id BIGINT NOT NULL,
    photo_id BIGINT NOT NULL,

    added_at TIMESTAMPTZ NOT NULL DEFAULT now(),

    PRIMARY KEY (user_id, photo_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE
    );

    CREATE TABLE photo_albums_tags(
        tag_id BIGINT NOT NULL,
        photo_album_id BIGINT NOT NULL,
        PRIMARY KEY(tag_id, photo_album_id),
        FOREIGN KEY (tag_id) REFERENCES tags(id) ON DELETE CASCADE,
        FOREIGN KEY (photo_album_id) REFERENCES photo_albums(id) ON DELETE CASCADE
    );

    CREATE TABLE photo_albums_photos (
        photo_album_id BIGINT NOT NULL,
        photo_id BIGINT NOT NULL,

        position INTEGER NOT NULL,
        added_at TIMESTAMPTZ NOT NULL DEFAULT now(),

        PRIMARY KEY (photo_album_id, photo_id),
        FOREIGN KEY (photo_album_id) REFERENCES photo_albums(id) ON DELETE CASCADE,
        FOREIGN KEY (photo_id) REFERENCES photos(id) ON DELETE CASCADE
    );

    -- INDEX

    --index photo
    CREATE INDEX idx_photos_uploaded_by_user_id
    ON photos(uploaded_by_user_id);

    -- index photo_albums
    CREATE INDEX idx_photo_albums_owner_user_id
    ON photo_albums(owner_user_id);

    --index users-photo-albums
    CREATE INDEX idx_users_photo_albums_photo_album_id
    ON users_photo_albums(photo_album_id);

    -- index photo_person_tags
    CREATE INDEX idx_photo_person_tags_tagged_by_user_id
    ON photo_person_tags(tagged_by_user_id);

    --index photo_albums_photos
    CREATE INDEX idx_photo_albums_photos_photo_id
    ON photo_albums_photos(photo_id);

    --index photo_contributors
    CREATE INDEX idx_photo_contributors_photo_id
    ON photo_contributors(photo_id);

    --index photo_album_tags
    CREATE INDEX idx_photo_albums_tags_photo_album_id
    ON photo_albums_tags(photo_album_id);





