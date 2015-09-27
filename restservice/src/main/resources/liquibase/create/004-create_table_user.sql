--liquibase formatted sql

CREATE TABLE users (
  id       SERIAL PRIMARY KEY UNIQUE NOT NULL,
  login    VARCHAR(50) UNIQUE        NOT NULL,
  password VARCHAR(30)               NOT NULL,
  role_id  INT                       NOT NULL,
  FOREIGN KEY (role_id) REFERENCES role (id)
);
