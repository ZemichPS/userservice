CREATE SCHEMA IF NOT EXISTS app;
DROP TABLE IF EXISTS app.users;
CREATE TABLE app.users
(
    id         uuid         NOT NULL,
    telegram_user_id VARCHAR(255)      NULL,
    first_name VARCHAR(255) NULL,
    last_name  VARCHAR(255) NULL,
    email      VARCHAR(255) NULL,
    password   VARCHAR(255) NULL,
    birth_date date         NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);