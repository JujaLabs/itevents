--liquibase formatted sql

--changeset vaa25:2
INSERT INTO currency (name) VALUES
  ('USD'),
  ('Гривна'),
  ('Euro');

--rollback DELETE FROM currency;
