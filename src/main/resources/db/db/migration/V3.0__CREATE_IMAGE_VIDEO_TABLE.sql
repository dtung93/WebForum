CREATE TABLE video (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       title VARCHAR(50),
                       duration INT,
                       content LONGBLOB,
                       description VARCHAR(255),
                       file_type VARCHAR(5),
                       file_size INT,
                       post_id BIGINT,
                       removal_flag NUMERIC(1,0) NOT NULL DEFAULT 0,
                       FOREIGN KEY (post_id) REFERENCES post (id)
);

CREATE TABLE image (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       file_name VARCHAR(50),
                       content LONGBLOB,
                       description VARCHAR(255),
                       file_type VARCHAR(5),
                       file_size INT,
                       post_id BIGINT,
                       removal_flag NUMERIC(1,0) NOT NULL DEFAULT 0,
                       FOREIGN KEY (post_id) REFERENCES post (id)
);
