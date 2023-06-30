ALTER TABLE user_photo
    ADD COLUMN is_profile_photo NUMERIC(1,0) NOT NULL DEFAULT 0;
