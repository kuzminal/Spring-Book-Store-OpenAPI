ALTER TABLE IF EXISTS bookstore.genres
    ADD COLUMN IF NOT EXISTS version bigint;

ALTER TABLE IF EXISTS bookstore.books
    ADD COLUMN IF NOT EXISTS version bigint;

ALTER TABLE IF EXISTS bookstore.authors
    ADD COLUMN IF NOT EXISTS version bigint;