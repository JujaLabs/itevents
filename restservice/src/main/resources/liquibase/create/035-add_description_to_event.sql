-- liquibase formatted sql
-- changeset vaa25:35

ALTER TABLE event ADD description VARCHAR;

-- rollback ALTER TABLE event DROP COLUMN description;
