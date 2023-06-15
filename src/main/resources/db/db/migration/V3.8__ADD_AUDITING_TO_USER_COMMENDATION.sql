ALTER TABLE user_commendation
   ADD `created_date` DATE NOT NULL;
ALTER TABLE  user_commendation
  ADD `created_by` VARCHAR(20) NOT NULL;

ALTER  TABLE  user_commendation
    ADD `updated_date` DATE NOT NULL;
ALTER TABLE user_commendation
  ADD  `updated_by`  VARCHAR(20) NOT NULL;

ALTER TABLE user_commendation
    ADD `removal_flag` NUMERIC(1,0) NOT NULL DEFAULT 0;
