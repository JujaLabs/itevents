--liquibase formatted sql

--changeset vaa25:8
CREATE TABLE role (
  id   SERIAL PRIMARY KEY UNIQUE NOT NULL,
  name VARCHAR(20) UNIQUE
);