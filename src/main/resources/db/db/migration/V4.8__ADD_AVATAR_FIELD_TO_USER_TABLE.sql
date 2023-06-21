CREATE TABLE user_photo(
    id VARCHAR(300) NOT NULL,
    user_id BIGINT,
    name VARCHAR(50),
    file_tag VARCHAR(10),
    file_type VARCHAR(20),
    created_by VARCHAR(20),
    created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_by VARCHAR(20),
    updated_date  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    removal_flag NUMERIC(1,0) NOT NULL DEFAULT 0
);

ALTER table user_photo
    ADD CONSTRAINT fk_user_id foreign key (user_id) references user(id);

