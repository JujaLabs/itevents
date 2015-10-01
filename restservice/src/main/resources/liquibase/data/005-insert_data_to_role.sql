--liquibase formatted sql

--changeset vaa25:5
INSERT INTO role (id, name) VALUES
  (-1, 'guest'),
  (-2, 'admin'),
  (-3, 'subscriber');

--rollback DELETE FROM role;
