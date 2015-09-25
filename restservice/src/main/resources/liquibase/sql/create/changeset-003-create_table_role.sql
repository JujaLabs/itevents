--liquibase formatted sql

CREATE TABLE role (
  id   SERIAL PRIMARY KEY UNIQUE NOT NULL,
  name VARCHAR(20) UNIQUE
);