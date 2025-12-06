-- liquibase formatted sql

--changeset Maltsev:create-users-table
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(40) NOT NULL UNIQUE,
    email    VARCHAR(40) NOT NULL UNIQUE,
    password VARCHAR(40) NOT NULL,
    address  VARCHAR(40),
    balance  DECIMAL(12, 2)       DEFAULT 0 check ( balance >= 0 ),
    role     VARCHAR(40) NOT NULL DEFAULT 'USER'
);
--rollback DROP TABLE users
