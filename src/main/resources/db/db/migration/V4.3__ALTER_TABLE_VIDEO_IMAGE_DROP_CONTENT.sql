ALTER TABLE video
        DROP COLUMN content;

ALTER TABLE image
        DROP COLUMN content;

ALTER TABLE file
        DROP COLUMN file_data;

ALTER TABLE message_file
        DROP COLUMN file_data;
