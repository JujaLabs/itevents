-- liquibase formatted sql
-- changeset Igor:34

ALTER TABLE user_event
ADD COLUMN deleteDate TIMESTAMP DEFAULT NULL;

-- rollback ALTER TABLE user_event
-- DROP COLUMN deleteDate;