ALTER TABLE file
    ADD COLUMN web_view_link VARCHAR(255),
    ADD COLUMN thumb_nail_Link VARCHAR(255),
    ADD COLUMN embed_link VARCHAR(255),
    ADD COLUMN download_link VARCHAR(255);


ALTER TABLE message_file
    ADD COLUMN web_view_link VARCHAR(255),
    ADD COLUMN thumb_nail_Link VARCHAR(255),
    ADD COLUMN embed_link VARCHAR(255),
    ADD COLUMN download_link VARCHAR(255);


ALTER TABLE video
    ADD COLUMN web_view_link VARCHAR(255),
    ADD COLUMN thumb_nail_Link VARCHAR(255),
    ADD COLUMN embed_link VARCHAR(255),
    ADD COLUMN download_link VARCHAR(255);


ALTER TABLE image
    ADD COLUMN web_view_link VARCHAR(255),
    ADD COLUMN thumb_nail_Link VARCHAR(255),
    ADD COLUMN embed_link VARCHAR(255),
    ADD COLUMN download_link VARCHAR(255);

