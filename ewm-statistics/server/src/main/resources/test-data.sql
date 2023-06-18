ALTER TABLE users
    ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE requests
    ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE items
    ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE bookings
    ALTER COLUMN ID RESTART WITH 1;
ALTER TABLE comments
    ALTER COLUMN ID RESTART WITH 1;


INSERT INTO users (name, email)
VALUES ('userName', 'userEmail@mail.com'),
       ('userName2', 'userEmail2@mail.com');

INSERT INTO requests (description, requestor_id)
VALUES ('requestDescription', 1);

INSERT INTO items (name, description, is_available, owner_id, request_id)
VALUES ('itemName', 'itemDescription', 'true', 1, null),
       ('itemName2', 'itemDescription2', 'true', 1, 1),
       ('itemName3', 'itemDescription3', 'true', 2, 1);

INSERT INTO bookings (start_date, end_date, item_id, booker_id, status)
VALUES (DATEADD('MINUTE', 1, CURRENT_TIMESTAMP), DATEADD('MINUTE', 5, CURRENT_TIMESTAMP), 1, 1, 'WAITING'),
       (DATEADD('MINUTE', 6, CURRENT_TIMESTAMP), DATEADD('MINUTE', 10, CURRENT_TIMESTAMP), 1, 1, 'APPROVED'),
       (DATEADD('MINUTE', 11, CURRENT_TIMESTAMP), DATEADD('MINUTE', 15, CURRENT_TIMESTAMP), 2, 2, 'REJECTED'),
       (DATEADD('MINUTE', 16, CURRENT_TIMESTAMP), DATEADD('MINUTE', 20, CURRENT_TIMESTAMP), 1, 2, 'APPROVED');

INSERT INTO comments (text, item_id, author_id, created)
VALUES ('Comment text', 1, 2, CURRENT_TIMESTAMP),
       ('Comment2 text', 1, 1, CURRENT_TIMESTAMP),
       ('Comment3 text', 1, 1, CURRENT_TIMESTAMP);


