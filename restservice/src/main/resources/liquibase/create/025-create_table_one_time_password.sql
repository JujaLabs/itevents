--liquibase formatted sql

--changeset Igor:025
CREATE TABLE one_time_password (
  user_id INT,
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  password VARCHAR NOT NULL UNIQUE PRIMARY KEY,
  expiration_date TIMESTAMP DEFAULT NULL
);