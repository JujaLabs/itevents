--liquibase formatted sql

--changeset vaa25:12
INSERT INTO technology (id, name) VALUES
  (-1, 'Java'),
  (-2, 'JavaScript'),
  (-3, 'PHP'),
  (-4, 'Liquibase'),
  (-5, 'Gradle'),
  (-6, 'Maven'),
  (-7, 'Ant'),
  (-8, 'Spring'),
  (-9, 'MyBatis'),
  (-10, 'SQL');

--rollback DELETE FROM technology;
