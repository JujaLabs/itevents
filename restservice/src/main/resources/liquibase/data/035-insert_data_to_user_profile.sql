--liquibase formatted sql
--changeset Igor:35
UPDATE user_profile SET isActive = TRUE
where isActive = false;

--rollback DELETE FROM user_profile;
