CREATE TABLE user (
                         `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                         `username` VARCHAR(20) NOT NULL,
                         `password` VARCHAR(200) NOT NULL,
                         `email` VARCHAR(50) NOT NULL,
                         `phone` VARCHAR(12) NOT NULL,
                         `address` VARCHAR(100),
                         `badge` VARCHAR(30) NOT NULL ,
                         `created_date` DATE NOT NULL,
                         `created_by` VARCHAR(20) NOT NULL,
                         `updated_date` DATE NOT NULL,
                         `updated_by`  VARCHAR(20) NOT NULL,
                         `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0,
                         PRIMARY KEY (`id`)
);

CREATE TABLE post (
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                        `created_date` DATE NOT NULL,
                        `created_by` VARCHAR(20) NOT NULL,
                        `updated_date` DATE NOT NULL,
                        `updated_by`  VARCHAR(20) NOT NULL,
                        `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0,
                        PRIMARY KEY (`id`)
);

CREATE TABLE file(
                        `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
                        `file_name` VARCHAR(50) NOT NULL,
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

CREATE TABLE thread (
                        id BIGINT NOT NULL AUTO_INCREMENT,
                        title VARCHAR(100) NOT NULL,
                        category ENUM('TECH', 'NEWS', 'REVIEWS', 'ANNOUNCEMENT','NATURE','GAME','SPORTS','STUDIES','HEALTH','GENERAL','STORIES','TRADE') NOT NULL,
                        username VARCHAR(20) NOT NULL,
                        last_replied VARCHAR(20) NOT NULL,
                        views BIGINT NOT NULL DEFAULT 0,
                        `created_date` DATE NOT NULL,
                        `created_by` VARCHAR(20) NOT NULL,
                        `updated_date` DATE NOT NULL,
                        `updated_by`  VARCHAR(20) NOT NULL,
                        `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0,
                        PRIMARY KEY (id)
);


