-- liquibase formatted sql
-- changeset Igor:34

 ALTER TABLE user_event
  DROP CONSTRAINT user_event_pkey,
  ADD COLUMN unassignDate TIMESTAMP DEFAULT NULL,
  ADD COLUMN unassignReason VARCHAR(250)DEFAULT NULL,
  ADD id SERIAL PRIMARY KEY NOT NULL;

-- rollback ALTER TABLE user_event
-- DROP CONSTRAINT user_event_pkey,
-- DROP COLUMN unassignDate,
-- DROP COLUMN  unassignReason,
-- DROP COLUMN id,
-- ADD PRIMARY KEY (user_id, event_id),
-- UNIQUE (user_id, event_id);
