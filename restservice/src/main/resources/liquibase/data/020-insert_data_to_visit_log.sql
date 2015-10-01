--liquibase formatted sql

--changeset vaa25:20
INSERT INTO visit_log (id, event_id, date, user_id) VALUES
  (-1, -1, '20.07.2016', -1),
  (-2, -1, '20.07.2016', -2),
  (-3, -2, '20.07.2016', -1),
  (-4, -2, '20.08.2016', -2),
  (-5, -3, '20.09.2016', -3),
  (-6, -2, '20.08.2016', -3),
  (-7, -1, '20.09.2016', -3);

--rollback DELETE FROM visit_log;
