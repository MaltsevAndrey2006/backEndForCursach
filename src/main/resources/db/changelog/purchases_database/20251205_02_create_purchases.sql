-- liquibase formatted sql

--changeset Maltsev:create-purchases-table
CREATE TABLE purchases
(
    id                  SERIAL PRIMARY KEY,
    date_of_transaction TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    sum                 DECIMAL(12,2)    NOT NULL check ( sum > 0 ),
    user_id             BIGINT    NOT NULL,
    music_id            BIGINT    NOT NULL
);
--rollback DROP TABLE purchases
