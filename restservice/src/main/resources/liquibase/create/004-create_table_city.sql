--liquibase formatted sql

--changeset vaa25:4
CREATE TABLE city (
  id      SERIAL PRIMARY KEY UNIQUE NOT NULL,
  name    VARCHAR(40)               NOT NULL,
  details VARCHAR(120),
  point   GEOMETRY(POINT) UNIQUE    NOT NULL,
  UNIQUE (name, details)
);
