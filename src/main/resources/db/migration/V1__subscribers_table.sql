CREATE SEQUENCE subscriber_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE subscribers (
    id BIGINT NOT NULL,
    chatroom VARCHAR(255),
    nickname VARCHAR(255),
    PRIMARY KEY (id)
)