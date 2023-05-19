CREATE TABLE role (
                      id INT NOT NULL AUTO_INCREMENT PRIMARY KEY ,
                      name VARCHAR(10)NOT NULL
);

CREATE TABLE user_role (
                           user_id BIGINT NOT NULL,
                           role_id INT NOT NULL,
                           CONSTRAINT user_id_fk foreign key(user_id) references user(id),
                           CONSTRAINT role_id_fk foreign key(role_id) references role(id)
)
