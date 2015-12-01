--liquibase formatted sql

--changeset vaa25:30
ALTER TABLE user_profile ALTER COLUMN password TYPE VARCHAR(80);

--rollback ALTER TABLE user_profile ALTER COLUMN password TYPE VARCHAR (30);
