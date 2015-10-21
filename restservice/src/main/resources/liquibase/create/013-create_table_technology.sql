--liquibase formatted sql

--changeset vaa25:13
CREATE TABLE technology (
  id   SERIAL PRIMARY KEY UNIQUE NOT NULL,
  name VARCHAR(40) UNIQUE        NOT NULL
);
