ALTER TABLE thread
    MODIFY COLUMN created_by VARCHAR(20)  ;

ALTER TABLE thread
    MODIFY COLUMN updated_by VARCHAR(20) ;

ALTER TABLE post
    MODIFY COLUMN created_by VARCHAR(20) ;

ALTER TABLE post
    MODIFY COLUMN updated_by VARCHAR(20)  ;

ALTER TABLE file
    MODIFY COLUMN created_by VARCHAR(20) ;

ALTER TABLE file
    MODIFY COLUMN updated_by VARCHAR(20) ;

ALTER TABLE user_file
    MODIFY COLUMN created_by VARCHAR(20) ;

ALTER TABLE user_file
    MODIFY COLUMN updated_by VARCHAR(20) ;


ALTER TABLE message_file
    MODIFY COLUMN created_by VARCHAR(20) ;

ALTER TABLE message_file
    MODIFY COLUMN updated_by VARCHAR(20)  ;
