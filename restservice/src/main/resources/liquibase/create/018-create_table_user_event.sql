--liquibase formatted sql

--changeset Igor:18
 CREATE TABLE user_event (
            user_id INT NOT NULL,
            event_id INT NOT NULL,
            FOREIGN KEY (user_id) REFERENCES user_profile(id),
            FOREIGN KEY (event_id) REFERENCES event(id),
            PRIMARY KEY (user_id, event_id),
            UNIQUE (user_id, event_id)
            );
