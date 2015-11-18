--liquibase formatted sql

--changeset Igor:32
INSERT INTO user_event (user_id, event_id) VALUES
  (-1, -1),
  (-1, -2),
  (-1, -3),
  (-2, -1);

--rollback DELETE FROM user_event;
