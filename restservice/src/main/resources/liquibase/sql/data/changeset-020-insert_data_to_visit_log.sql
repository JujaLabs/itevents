INSERT INTO visit_log (event_id, date, user_id) VALUES
  (1, '20.07.2016', 1),
  (1, '20.07.2016', 2),
  (2, '20.07.2016', 1),
  (2, '20.08.2016', 2),
  (3, '20.09.2016', 3),
  (2, '20.08.2016', 3),
  (1, '20.09.2016', 3);

--rollback DELETE * FROM visit_log;
