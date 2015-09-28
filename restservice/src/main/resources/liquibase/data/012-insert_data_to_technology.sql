--liquibase formatted sql

--changeset vaa25:12
INSERT INTO technology (name) VALUES
  ('Java'),
  ('JavaScript'),
  ('PHP'),
  ('Liquibase'),
  ('Gradle'),
  ('Maven'),
  ('Ant'),
  ('Spring'),
  ('MyBatis'),
  ('SQL');

--rollback DELETE FROM technology;
