--liquibase formatted sql

--changeset vaa25:11
CREATE TABLE user_profile (
  id       SERIAL PRIMARY KEY UNIQUE NOT NULL,
  login    VARCHAR(50) UNIQUE        NOT NULL,
  password VARCHAR(30)               NOT NULL,
  role_id  INT                       NOT NULL,
  FOREIGN KEY (role_id) REFERENCES role
);
