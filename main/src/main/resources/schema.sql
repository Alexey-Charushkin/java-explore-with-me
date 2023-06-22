drop table IF EXISTS statistics;

CREATE TABLE IF NOT EXISTS statistics
(
    id    INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    app  varchar(100),
    uri varchar(320) ,
    ip varchar(100),
    timestamp TIMESTAMP WITHOUT TIME ZONE
    );