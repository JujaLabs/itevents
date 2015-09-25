INSERT INTO users (login, password, role_id) VALUES
  ('guest', 'guest', 1),
  ('anakin@email.com', 'alex', 2),
  ('kuchin@email.com', 'viktor', 2),
  ('vlasov@email.com', 'alex', 3);

--rollback DELETE * FROM users;
