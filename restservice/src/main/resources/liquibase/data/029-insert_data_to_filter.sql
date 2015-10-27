--liquibase formatted sql

--changeset vaa25:29
INSERT INTO filter (id, row_limit, create_date, city_id, free, longitude, latitude, radius) VALUES
  (-1, NULL, '10.07.2015', -1, NULL, NULL, NULL, NULL),
  (-2, NULL, '11.07.2015', NULL, TRUE, NULL, NULL, NULL),
  (-3, NULL, '12.07.2015', -1, FALSE, NULL, NULL, NULL),
  (-4, NULL, '13.07.2015', NULL, NULL, NULL, NULL, NULL),
  (-5, NULL, '14.07.2015', -1, FALSE, NULL, NULL, NULL)

--rollback DELETE FROM filter;