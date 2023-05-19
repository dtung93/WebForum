ALTER TABLE user_file_post
    ADD file_id BIGINT NOT NULL;

ALTER TABLE user_file_post
    ADD CONSTRAINT user_file_post_file FOREIGN KEY (file_id) REFERENCES file(id);

ALTER TABLE user_file
    ADD user_id BIGINT NOT NULL;

ALTER TABLE user_file
    ADD CONSTRAINT user_file_user FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE message_file
    ADD message_id BIGINT NOT NULL;

ALTER TABLE message_file
    ADD CONSTRAINT message_file_message FOREIGN KEY (message_id) REFERENCES message(id);


ALTER TABLE message
    ADD recipient VARCHAR(20) NOT NULL;

ALTER TABLE message
    MODIFY COLUMN content LONGBLOB NOT NULL;

ALTER TABLE post
    ADD COLUMN content LONGBLOB NOT NULL;

ALTER TABLE post
    ADD COLUMN user_id BIGINT NOT NULL;

ALTER TABLE post
    ADD COLUMN thread_id BIGINT NOT NULL ;

ALTER TABLE post
    ADD CONSTRAINT post_user_fk FOREIGN KEY (user_id) REFERENCES user(id);

ALTER TABLE post
    ADD CONSTRAINT post_thread_fk FOREIGN KEY (thread_id) REFERENCES thread(id);

