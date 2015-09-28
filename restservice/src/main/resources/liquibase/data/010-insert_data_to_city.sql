--liquibase formatted sql

--changeset vaa25:10
INSERT INTO city (name, details, point) VALUES
  ('Kyiv', NULL, 'POINT(30.523000 50.450500)'),
  ('Odessa', NULL, 'POINT(30.73333 46.46667)'),
  ('Boyarka', NULL, 'POINT(30.28861 50.32917)'),
  ('Odessa', 'Odessa, Florida, USA', 'POINT(-82.568333 28.183333)');

--rollback DELETE FROM city;
