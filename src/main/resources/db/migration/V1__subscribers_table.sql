CREATE SEQUENCE user_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE users (
    id BIGINT NOT NULL,
    nickname VARCHAR(255),
    age INTEGER,
    gender VARCHAR(255),
    PRIMARY KEY (id)
)