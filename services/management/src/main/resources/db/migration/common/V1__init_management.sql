CREATE TABLE IF NOT EXISTS users
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    email CHARACTER VARYING,
    password CHARACTER VARYING,
    authority CHARACTER VARYING,
    active boolean,
    activation_token uuid
);

CREATE TABLE IF NOT EXISTS tenants
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name CHARACTER VARYING
);

CREATE TABLE IF NOT EXISTS managers
(
    id BIGSERIAL NOT NULL PRIMARY KEY,
    name CHARACTER VARYING
);

CREATE UNIQUE INDEX users_email_uindex ON users (email);