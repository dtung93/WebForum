ALTER TABLE file
        MODIFY COLUMN file_type varchar(20);

ALTER TABLE message_file
        MODIFY COLUMN file_type varchar(20);

ALTER TABLE video
    MODIFY COLUMN file_type varchar(20);

ALTER TABLE image
    MODIFY COLUMN file_type varchar(20);
