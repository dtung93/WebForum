ALTER TABLE user_commendation
        MODIFY COLUMN commendation_type VARCHAR(20);

ALTER TABLE user_commendation
        ADD COLUMN commendation VARCHAR(20);

ALTER TABLE post_commendation
        MODIFY COLUMN commendation_type VARCHAR(20);


