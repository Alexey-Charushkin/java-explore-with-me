drop table IF EXISTS users;
drop table IF EXISTS categories;
drop table IF EXISTS locations;
drop table IF EXISTS events;

CREATE TABLE IF NOT EXISTS users(
    id  INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    email  VARCHAR(254),
    name   VARCHAR(250)
    );

CREATE TABLE IF NOT EXISTS categories(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    name   VARCHAR(50) UNIQUE
    );

CREATE TABLE IF NOT EXISTS locations(
     id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
     lat FLOAT,
     lon FLOAT
    );

CREATE TABLE IF NOT EXISTS events(
    id INT GENERATED ALWAYS AS IDENTITY PRIMARY KEY NOT NULL UNIQUE,
    annotation VARCHAR(2000),
    category_id INT,
    confirmed_requests INT,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    description VARCHAR(7000),
    event_date TIMESTAMP WITHOUT TIME ZONE,
    initiator_id INT,
    location_id INT,
    paid VARCHAR(5),
    participant_limit INT,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    request_moderation VARCHAR(5),
    state VARCHAR(10),
    title VARCHAR(120)
    CONSTRAINT fk_categories FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_users FOREIGN KEY (initiator_id) REFERENCES users (id)
    CONSTRAINT fk_locations FOREIGN KEY (location_id) REFERENCES locations (id)
    );