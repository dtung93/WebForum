ALTER TABLE user_saved_thread
    RENAME COLUMN post_id TO thread_id;
ALTER TABLE file
    ADD tag VARCHAR(10);

RENAME TABLE user_commendations to user_commendation;

CREATE TABLE user_file (
                           `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                           `file_name` VARCHAR(50) NOT NULL,
                            `tag` VARCHAR(10) NOT NULL,
                           `file_type` VARCHAR(10) NOT NULL ,
                           `file_size` INT NOT NULL,
                           `file_data` LONGBLOB,
                           `created_date` DATE NOT NULL,
                           `created_by` VARCHAR(20) NOT NULL,
                           `updated_date` DATE NOT NULL,
                           `updated_by`  VARCHAR(20) NOT NULL,
                           `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0,
                           PRIMARY KEY (id)
);

CREATE TABLE message_file (
                              `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                              `file_name` VARCHAR(50) NOT NULL,
                              `tag` VARCHAR(10) NOT NULL,
                              `file_type` VARCHAR(10) NOT NULL ,
                              `file_size` INT NOT NULL,
                              `file_data` LONGBLOB,
                              `created_date` DATE NOT NULL,
                              `created_by` VARCHAR(20) NOT NULL,
                              `updated_date` DATE NOT NULL,
                              `updated_by`  VARCHAR(20) NOT NULL,
                              `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0,
                              PRIMARY KEY (id)
);


