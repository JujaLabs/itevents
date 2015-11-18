--liquibase formatted sql

--changeset vaa25:17
CREATE TABLE event_technology (
  event_id      INT NOT NULL,
  technology_id INT NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event,
  FOREIGN KEY (technology_id) REFERENCES technology,
  PRIMARY KEY (event_id, technology_id),
  UNIQUE (event_id, technology_id)
);
