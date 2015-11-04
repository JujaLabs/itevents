--liquibase formatted sql

--changeset Igor:025
CREATE TABLE user_otp (
  user_id INT NOT NULL UNIQUE PRIMARY KEY,
  FOREIGN KEY (user_id) REFERENCES user_profile(id) ON DELETE CASCADE,
  OTP VARCHAR NOT NULL,
  creationDate TIMESTAMP,
  expirationDate TIMESTAMP DEFAULT NULL
)