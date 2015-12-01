--liquibase formatted sql

--changeset vaa25:29

UPDATE user_profile
SET subscribed = TRUE
WHERE id != -1;

--rollback UPDATE TABLE user_profile SET subscribed = FALSE WHERE id != -1;
