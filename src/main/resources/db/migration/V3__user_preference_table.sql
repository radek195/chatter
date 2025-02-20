CREATE SEQUENCE user_preference_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE user_preferences (
    id BIGINT NOT NULL,
    min_age integer,
    max_age integer,
    PRIMARY KEY (id)
);

ALTER TABLE users ADD COLUMN user_preference_id BIGINT;

ALTER TABLE users
    ADD CONSTRAINT fk_user_preference
    FOREIGN KEY (user_preference_id) REFERENCES user_preferences(id);