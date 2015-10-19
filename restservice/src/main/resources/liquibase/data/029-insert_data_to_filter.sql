--liquibase formatted sql

--changeset vaa25:29
INSERT INTO filter (id, limit, city_id, free, longitude, latitude, radius) VALUES
  (-1, NULL, -1, NULL, NULL, NULL, NULL),
  (-2, NULL, NULL, TRUE, NULL, NULL, NULL),
  (-3, NULL, -1, FALSE, NULL, NULL, NULL),
  (-4, NULL, NULL, NULL, NULL, NULL, NULL)

--rollback DELETE FROM filter;