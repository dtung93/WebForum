ALTER TABLE user
    ADD CONSTRAINT check_login_attempt_limit CHECK ( login_attempt <6 );
