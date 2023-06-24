drop table IF EXISTS users;
drop table IF EXISTS categories;

CREATE TABLE IF NOT EXISTS users(
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    email  varchar(254),
    name   varchar(250)
    );

CREATE TABLE IF NOT EXISTS categories(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    name   varchar(50) UNIQUE
    );