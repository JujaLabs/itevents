--liquibase formatted sql

--changeset vaa25:26
CREATE TABLE user_filter (
  user_id INT NOT NULL,
  filter_id INT UNIQUE NOT NULL,
  FOREIGN KEY (user_id) REFERENCES user_profile,
  FOREIGN KEY (filter_id) REFERENCES filter,
  PRIMARY KEY (user_id, filter_id),
  UNIQUE (user_id, filter_id)
);
