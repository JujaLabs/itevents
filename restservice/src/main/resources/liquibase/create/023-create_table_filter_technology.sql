--liquibase formatted sql

--changeset vaa25:23
CREATE TABLE filter_technology (
  filter_id     INT NOT NULL,
  technology_id INT NOT NULL,
  FOREIGN KEY (filter_id) REFERENCES filter,
  FOREIGN KEY (technology_id) REFERENCES technology,
  PRIMARY KEY (filter_id, technology_id),
  UNIQUE (filter_id, technology_id)
);
