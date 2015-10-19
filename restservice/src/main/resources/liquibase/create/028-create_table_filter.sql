--liquibase formatted sql
--changeset vaa25:28
CREATE TABLE filter (
  id        SERIAL UNIQUE PRIMARY KEY NOT NULL,
  "limit" INT,
  city_id   INT,
  free      BOOLEAN,
  longitude FLOAT,
  latitude  FLOAT,
  radius    FLOAT,
  FOREIGN KEY (city_id) REFERENCES city
);
