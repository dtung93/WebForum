ALTER TABLE post_commendation
    DROP COLUMN commendation_type;

ALTER TABLE post_commendation
    ADD COLUMN commendation ENUM('THUMBS_UP','THUMBS_DOWN','MONEY','HEART');
