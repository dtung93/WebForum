ALTER TABLE user
    MODIFY COLUMN created_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';

ALTER TABLE user
    MODIFY COLUMN updated_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';


ALTER TABLE thread
    MODIFY COLUMN created_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';
ALTER TABLE thread
    MODIFY COLUMN updated_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';


ALTER TABLE post
    MODIFY COLUMN created_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';
ALTER TABLE post
    MODIFY COLUMN updated_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';


ALTER TABLE file
    MODIFY COLUMN created_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';

ALTER TABLE file
    MODIFY COLUMN updated_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';


ALTER TABLE message_file
    MODIFY COLUMN created_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';
ALTER TABLE message_file
    MODIFY COLUMN updated_by VARCHAR(20)  DEFAULT 'ADMIN TUNG';
