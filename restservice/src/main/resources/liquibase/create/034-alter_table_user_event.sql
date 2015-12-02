-- liquibase formatted sql
-- changeset Igor:34

ALTER TABLE user_event
  DROP CONSTRAINT user_event_pkey,
  ADD COLUMN unassign_date TIMESTAMP DEFAULT NULL,
  ADD COLUMN unassign_reason VARCHAR(250)DEFAULT NULL,
  ADD id SERIAL PRIMARY KEY NOT NULL;

-- rollback ALTER TABLE user_event
-- DROP CONSTRAINT user_event_pkey,
-- DROP COLUMN unassign_date,
-- DROP COLUMN  unassign_reason,
-- DROP COLUMN id,
-- ADD PRIMARY KEY (user_id, event_id),
-- UNIQUE (user_id, event_id);
