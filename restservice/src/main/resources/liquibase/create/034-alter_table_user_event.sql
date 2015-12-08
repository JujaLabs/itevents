-- liquibase formatted sql
-- changeset Igor:34

ALTER TABLE user_event
  DROP CONSTRAINT user_event_pkey,
  ADD COLUMN created_date TIMESTAMP DEFAULT now(),
  ADD COLUMN deleted_date TIMESTAMP DEFAULT NULL ,
  ADD COLUMN deleted_reason VARCHAR DEFAULT NULL,
  ADD id SERIAL PRIMARY KEY NOT NULL;

-- rollback ALTER TABLE user_event
-- DROP CONSTRAINT user_event_pkey,
-- DROP COLUMN created_date,
-- DROP COLUMN deleted_date,
-- DROP COLUMN deleted_reason,
-- DROP COLUMN id,
-- ADD PRIMARY KEY (user_id, event_id),
-- UNIQUE (user_id, event_id);
