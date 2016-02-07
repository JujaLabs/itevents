--liquibase formatted sql

--changeset Igor:025
CREATE TABLE one_time_password (
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  password VARCHAR NOT NULL UNIQUE PRIMARY KEY,
  creation_date TIMESTAMP DEFAULT NULL,
  expiration_date TIMESTAMP DEFAULT NULL
);