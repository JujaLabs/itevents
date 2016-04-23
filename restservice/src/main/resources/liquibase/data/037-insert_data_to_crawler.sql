--liquibase formatted sql

--changeset vaa25:37
INSERT INTO crawler (id, integration_name, integration_event_url, event_id)
VALUES
  (-1, 'SampleIntegration', 'http://localhost:8080/example', -1),
  (-2, 'SampleIntegration', 'http://localhost:8080/example2', -2),
  (-3, 'AnotherIntegration', 'http://www.another.com/example1', -1);

--rollback DELETE FROM crawler;
