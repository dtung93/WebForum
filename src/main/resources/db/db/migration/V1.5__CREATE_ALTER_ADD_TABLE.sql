CREATE TABLE post_commendation (
                                   user_id BIGINT NOT NULL,
                                   commendation_type VARCHAR(255) NOT NULL,
                                   post_id BIGINT NOT NULL,
                                   count INT,
                                   `created_date` DATE NOT NULL,
                                   `created_by` VARCHAR(20) NOT NULL,
                                   `updated_date` DATE NOT NULL,
                                   `updated_by`  VARCHAR(20) NOT NULL,
                                   `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0,
                                   CONSTRAINT fk_post_commendations_post FOREIGN KEY (post_id) REFERENCES post(id)
);
CREATE INDEX index_post_commendation_user_id on post_commendation(user_id);
CREATE INDEX index_post_commendation_post_id on post_commendation(post_id);
