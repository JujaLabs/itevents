--liquibase formatted sql
--changeset vaa25:21
CREATE TABLE filter (
  id        SERIAL UNIQUE PRIMARY KEY NOT NULL,
  row_limit INT,
  create_date TIMESTAMP NOT NULL,
  city_id   INT,
  free      BOOLEAN,
  longitude FLOAT,
  latitude  FLOAT,
  radius    FLOAT,
  FOREIGN KEY (city_id) REFERENCES city
);
