--liquibase formatted sql

--changeset Igor:025
CREATE TABLE user_otp (
  user_id int NOT NULL UNIQUE PRIMARY KEY,
  FOREIGN KEY (user_id) REFERENCES user_profile ON DELETE CASCADE,
  OTP VARCHAR,
  creationDate TIMESTAMP,
  expirationDate TIMESTAMP DEFAULT NULL
)