--liquibase formatted sql

--changeset vaa25:24
INSERT INTO filter_technology (filter_id, technology_id) VALUES
  (-1, -1),
  (-2, -4),
  (-4, -1),
  (-4, -3),
  (-3, -5),
  (-2, -2),
  (-3, -3),
  (-1, -6),
  (-2, -7),
  (-1, -8),
  (-1, -9);

--rollback DELETE FROM filter_technology;
