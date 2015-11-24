-- liquibase formatted sql
-- changeset Igor:33

ALTER TABLE user_profile
    DROP CONSTRAINT user_profile_role_id_fkey,
    ADD CONSTRAINT user_profile_role_id_fkey
    FOREIGN KEY (role_id) REFERENCES role ON DELETE CASCADE;

-- rollback ALTER TABLE user_profile
--    DROP CONSTRAINT user_profile_role_id_fkey
--    ADD CONSTRAINT user_profile_role_id_fkey
--    FOREIGN KEY (role_id) REFERENCES role;