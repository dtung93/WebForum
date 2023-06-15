ALTER TABLE file
        DROP COLUMN download_link,
        DROP COLUMN embed_link,
        ADD COLUMN web_content_link VARCHAR(255);

ALTER TABLE message_file
    DROP COLUMN download_link,
    DROP COLUMN embed_link,
    ADD COLUMN web_content_link VARCHAR(255);

ALTER TABLE video
    DROP COLUMN download_link,
    DROP COLUMN embed_link,
    ADD COLUMN web_content_link VARCHAR(255);

ALTER TABLE image
    DROP COLUMN download_link,
    DROP COLUMN embed_link,
    ADD COLUMN web_content_link VARCHAR(255);
