--liquibase formatted sql

--changeset vaa25:28
ALTER TABLE user_profile ADD subscribed BOOL DEFAULT FALSE;
