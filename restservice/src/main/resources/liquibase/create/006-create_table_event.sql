--liquibase formatted sql
--changeset vaa25:6
CREATE TABLE event (
  id          SERIAL UNIQUE PRIMARY KEY NOT NULL,
  title       VARCHAR(255)              NOT NULL,
  event_date  TIMESTAMP                 NOT NULL,
  create_date TIMESTAMP DEFAULT NULL,
  reg_link    VARCHAR(255),
  address     VARCHAR(255),
  point       GEOMETRY(POINT),
  contact     VARCHAR(255),
  price       INT,
  currency_id INT,
  city_id     INT,
  FOREIGN KEY (currency_id) REFERENCES currency,
  FOREIGN KEY (city_id) REFERENCES city ON DELETE CASCADE,
  CHECK ((price IS NOT NULL AND currency_id IS NOT NULL) OR
         (price IS NULL AND currency_id IS NULL))
);
