--liquibase formatted sql

--changeset vaa25:15
CREATE TABLE visit_log (
  id       SERIAL PRIMARY KEY UNIQUE NOT NULL,
  event_id INT                       NOT NULL,
  date     DATE                      NOT NULL,
  user_id  INT                       NOT NULL,
  FOREIGN KEY (event_id) REFERENCES event,
  FOREIGN KEY (user_id) REFERENCES user_profile
);
