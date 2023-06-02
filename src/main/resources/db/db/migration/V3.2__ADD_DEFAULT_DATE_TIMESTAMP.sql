ALTER TABLE thread
    MODIFY COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE thread
    MODIFY COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE user
    MODIFY COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE user
    MODIFY COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE post
    MODIFY COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE post
    MODIFY COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


ALTER TABLE message
    ADD COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE message
    ADD COLUMN created_by VARCHAR(20);

ALTER TABLE message
    ADD COLUMN updated_date TIMESTAMP  DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE message
    ADD COLUMN updated_by VARCHAR(20);



ALTER TABLE post_commendation
    MODIFY COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE post_commendation
    MODIFY COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE user_file
    MODIFY COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER TABLE user_file
    MODIFY COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

