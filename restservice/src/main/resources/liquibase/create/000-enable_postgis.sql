--liquibase formatted sql

--changeset vaa25:0

CREATE EXTENSION POSTGIS;

--rollback DROP EXTENSION POSTGIS;
