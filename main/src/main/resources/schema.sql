drop table IF EXISTS users;

CREATE TABLE IF NOT EXISTS users
(
    id    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    email  varchar(254),
    name   varchar(250)
    );