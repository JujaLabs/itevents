--liquibase formatted sql

--changeset vaa25:27
INSERT INTO user_filter (user_id, filter_id) VALUES
  (-2, -1),
  (-3, -5),
  (-4, -3),
  (-2, -2),
  (-3, -4)

--rollback DELETE FROM user_filter;
