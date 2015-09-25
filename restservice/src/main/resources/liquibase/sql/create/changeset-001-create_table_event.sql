--liquibase formatted sql

CREATE TABLE event (
  id          SERIAL UNIQUE PRIMARY KEY NOT NULL,
  title       VARCHAR(255)              NOT NULL,
  event_date  TIMESTAMP                 NOT NULL,
  create_date TIMESTAMP DEFAULT NULL,
  reg_link    VARCHAR(255),
  address     VARCHAR(255),
  point       GEOMETRY(POINT),
  contact     VARCHAR(255)
);
