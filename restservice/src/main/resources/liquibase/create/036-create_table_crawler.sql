--liquibase formatted sql

--changeset vaa25:36
CREATE TABLE crawler (
  id                    SERIAL PRIMARY KEY UNIQUE NOT NULL,
  integration_name      VARCHAR(100)              NOT NULL,
  integration_event_url VARCHAR(510) UNIQUE       NOT NULL,
  event_id              INTEGER,
  FOREIGN KEY (event_id) REFERENCES event
);
