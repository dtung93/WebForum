ALTER TABLE video
    MODIFY COLUMN id VARCHAR(300) NOT NULL;

ALTER TABLE image
    MODIFY COLUMN id VARCHAR(300) NOT NULL;


-- Modify the 'id' column to VARCHAR
ALTER TABLE file
    MODIFY COLUMN id VARCHAR(300) NOT NULL;

-- Update the foreign key constraint
ALTER TABLE user_file_post
    MODIFY COLUMN file_id VARCHAR(300),
    ADD CONSTRAINT user_file_post_file FOREIGN KEY (file_id) REFERENCES file(id);
