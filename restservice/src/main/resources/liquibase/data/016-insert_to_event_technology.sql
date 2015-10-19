--liquibase formatted sql

--changeset vaa25:16
INSERT INTO event_technology (event_id, technology_id) VALUES
  (-1, -1),
  (-1, -4),
  (-4, -1),
  (-4, -3),
  (-5, -5),
  (-2, -2),
  (-3, -3),
  (-6, -6),
  (-7, -7),
  (-1, -8),
  (-1, -9);

--rollback DELETE FROM event_technology;
