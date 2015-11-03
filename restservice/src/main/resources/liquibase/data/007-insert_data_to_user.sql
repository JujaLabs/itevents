--liquibase formatted sql

--changeset vaa25:7
INSERT INTO user_profile (id, login, password, role_id, isActive) VALUES
  (-1, 'guest', 'guest', -1, true),
  (-2, 'anakin@email.com', 'alex', -2, true),
  (-3, 'kuchin@email.com', 'viktor', -2, true),
  (-4, 'vlasov@email.com', 'alex', -3, true);

--rollback DELETE FROM user_profile;
