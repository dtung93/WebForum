ALTER TABLE user_commendation
    MODIFY COLUMN created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;

ALTER  TABLE  user_commendation
    MODIFY COLUMN updated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


