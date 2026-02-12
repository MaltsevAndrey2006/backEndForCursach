-- liquibase formatted sql

--changeset Maltsev:create-users-table
CREATE TABLE users
(
    id       SERIAL PRIMARY KEY,
    login    VARCHAR(100) NOT NULL UNIQUE,
    email    VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(225) NOT NULL,
    address  VARCHAR(40),
    balance  DECIMAL(12, 2)       DEFAULT 0 check ( balance >= 0 ),
    role     VARCHAR(40) NOT NULL DEFAULT 'USER'
);
--rollback DROP TABLE users
