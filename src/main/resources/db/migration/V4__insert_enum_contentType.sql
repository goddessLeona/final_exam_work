
CREATE TYPE content_type AS ENUM ('PHOTO', 'VIDEO');

ALTER TABLE photo_albums
ADD COLUMN content_type content_type NOT NULL DEFAULT 'PHOTO';