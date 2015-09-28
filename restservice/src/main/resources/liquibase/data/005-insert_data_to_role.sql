--liquibase formatted sql

--changeset vaa25:5
INSERT INTO role (name) VALUES
  ('guest'),
  ('admin'),
  ('subscriber');

--rollback DELETE FROM role;
