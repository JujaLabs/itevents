--liquibase formatted sql

--changeset vaa25:1
SET datestyle TO Europian;

--rollback SET datestyle TO DEFAULT;
