ALTER TABLE role
    MODIFY COLUMN name VARCHAR(20);

INSERT INTO role(id,name) values(1,"ROLE_USER");
INSERT INTO role(id,name) values(2,"ROLE_MODERATOR");
INSERT INTO role(id,name) values (3,"ROLE_ADMIN");


