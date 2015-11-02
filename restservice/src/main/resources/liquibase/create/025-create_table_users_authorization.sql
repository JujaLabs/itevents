--liquibase formatted sql
--changeset garazd:25
CREATE TABLE users_authorization (
id         SERIAL       PRIMARY KEY UNIQUE NOT NULL,
email      VARCHAR(255)             UNIQUE NOT NULL,
password   VARCHAR(255)                    NOT NULL,
activation VARCHAR(255)             UNIQUE NOT NULL,
status     BOOLEAN                         NOT NULL DEFAULT FALSE);