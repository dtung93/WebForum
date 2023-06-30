ALTER TABLE user_photo
    ADD COLUMN view_link VARCHAR(255),
    ADD COLUMN download_link VARCHAR(255);

ALTER TABLE user_file
    ADD COLUMN view_link VARCHAR(255),
    ADD COLUMN download_link VARCHAR(255);
