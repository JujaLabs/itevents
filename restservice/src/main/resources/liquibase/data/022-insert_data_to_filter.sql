--liquibase formatted sql

--changeset vaa25:22
INSERT INTO filter (id, row_limit, create_date, city_id, free, longitude, latitude, radius) VALUES
  (-1, NULL, to_date('10.07.2015', 'DD.MM.YYYY'), -1, NULL, NULL, NULL, NULL),
  (-2, NULL, to_date('11.07.2015', 'DD.MM.YYYY'), NULL, TRUE, NULL, NULL, NULL),
  (-3, NULL, to_date('12.07.2015', 'DD.MM.YYYY'), -1, FALSE, NULL, NULL, NULL),
  (-4, NULL, to_date('13.07.2015', 'DD.MM.YYYY'), NULL, NULL, NULL, NULL, NULL),
  (-5, NULL, to_date('14.07.2015', 'DD.MM.YYYY'), -1, FALSE, NULL, NULL, NULL)

--rollback DELETE FROM filter;