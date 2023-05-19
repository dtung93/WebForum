CREATE TABLE user_commendations (
                                    user_id BIGINT NOT NULL,
                                    commendation_type VARCHAR(255) NOT NULL,
                                    count INT,
                                    CONSTRAINT fk_user_commendations_user FOREIGN KEY (user_id) REFERENCES user(id)
);
CREATE INDEX index_user_commendations_user_id ON user_commendations (user_id);

CREATE TABLE user_file_post (
                                id BIGINT NOT NULL AUTO_INCREMENT,
                                user_id BIGINT NOT NULL,
                                post_id BIGINT NOT NULL,
                                PRIMARY KEY (id),
                                CONSTRAINT fk_user_file_post_user FOREIGN KEY (user_id) REFERENCES user(id),
                                CONSTRAINT fk_user_file_post_post FOREIGN KEY (post_id) REFERENCES post(id)
);
CREATE INDEX index_user_file_post_user_id ON user_file_post (user_id);
CREATE INDEX index_user_file_post_post_id ON user_file_post (post_id);

CREATE TABLE user_saved_post (
                                  user_id BIGINT NOT NULL,
                                  post_id BIGINT NOT NULL,
                                  CONSTRAINT pk_user_saved_posts PRIMARY KEY (user_id, post_id),
                                  CONSTRAINT fk_user_saved_posts_user FOREIGN KEY (user_id) REFERENCES user(id),
                                  CONSTRAINT fk_user_saved_posts_post FOREIGN KEY (post_id) REFERENCES post(id)
);
CREATE INDEX index_user_saved_posts_user_id ON user_saved_post (user_id);

CREATE TABLE user_saved_thread (
                                    user_id BIGINT NOT NULL,
                                    post_id BIGINT NOT NULL,
                                    CONSTRAINT pk_user_saved_threads PRIMARY KEY (user_id, post_id),
                                    CONSTRAINT fk_user_saved_threads_user FOREIGN KEY (user_id) REFERENCES user(id),
                                    CONSTRAINT fk_user_saved_threads_post FOREIGN KEY (post_id) REFERENCES post(id)
);
CREATE INDEX index_user_saved_threads_user_id ON user_saved_thread (user_id);

CREATE TABLE message (
                         id BIGINT NOT NULL AUTO_INCREMENT,
                         user_id BIGINT NOT NULL,
                         content VARCHAR(255),
                         PRIMARY KEY (id),
                         CONSTRAINT fk_message_user FOREIGN KEY (user_id) REFERENCES user(id)
);
CREATE INDEX index_message_user_id ON message (user_id);

