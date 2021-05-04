INSERT INTO persons (first_name, last_name)
VALUES ('Nick', 'Houser'),
       ('Michael', 'Prime'),
       ('Jane', 'Brown'),
       ('Liza', 'Calber'),
       ('Robert', 'Beck');

INSERT INTO telephone_books (person_id)
VALUES (1),
       (2),
       (3),
       (4),
       (5);

INSERT INTO notes (telephone_book_id, contact_name, telephone_number)
VALUES (1, 'David', '+19546982514'),
       (1, 'Gregory', '+19546982515'),
       (2, 'Daddy', '+19816910510'),
       (2, 'Mom', '+19817126598'),
       (3, 'Boss', '+19312567866'),
       (3, 'Kelly', '+19213659845'),
       (4, 'MrEdward', '+18124569987'),
       (5, 'Do not pick it', '+19096888365'),
       (5, 'Bartender Mo', '+19815657899'),
       (4, 'Teacher Rob', '+19318455400');