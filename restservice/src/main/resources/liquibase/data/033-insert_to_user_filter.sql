--liquibase formatted sql

--changeset vaa25:33
INSERT INTO user_filter (user_id, filter_id) VALUES
  (-1, -1),
  (-4, -3),
  (-2, -2),
  (-3, -3)

--rollback DELETE FROM user_filter;
