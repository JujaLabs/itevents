--liquibase formatted sql

--changeset vaa25:2
INSERT INTO currency (id, name) VALUES
  (-1, 'USD'),
  (-2, 'Гривна'),
  (-3, 'Euro');

--rollback DELETE FROM currency;
