--liquibase formatted sql

--changeset vaa25:3
CREATE TABLE currency (
  id   SERIAL PRIMARY KEY UNIQUE NOT NULL,
  name VARCHAR(40) UNIQUE NOT NULL
);
