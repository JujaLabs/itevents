--liquibase formatted sql

--changeset vaa25:7
INSERT INTO user_profile (id, login, password, role_id) VALUES
  (-1, 'guest', 'guest', -1),
  (-2, 'anakin@email.com', 'alex', -2),
  (-3, 'kuchin@email.com', 'viktor', -2),
  (-4, 'vlasov@email.com', 'alex', -3);

--rollback DELETE FROM user_profile;
