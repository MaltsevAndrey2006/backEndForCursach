-- liquibase formatted sql

--changeset Maltsev:create-musics-table
CREATE TABLE musics
(
    id             SERIAL PRIMARY KEY,
    album_name     VARCHAR(40)    NOT NULL,
    group_name     VARCHAR(40)    NOT NULL,
    price          DECIMAL(12, 2) NOT NULL check ( price >= 0 ),
    count          INT            NOT NULL check ( count >= 0 ),
    description    TEXT           NOT NULL,
    release_date   DATE           NOT NULL,
    date_of_update DATE           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    img_url        TEXT           NOT NULL,
    song_url       TEXT           NOT NULL,
    test_song_name TEXT           NOT NULL
);
--rollback DROP TABLE musics
