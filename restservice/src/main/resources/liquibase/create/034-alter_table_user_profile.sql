--liquibase formatted sql
--changeset Igor:34

ALTER TABLE user_profile
ADD isActive BOOL DEFAULT FALSE;

--rollback DROP COLUMN isActive;
