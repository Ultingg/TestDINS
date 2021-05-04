INSERT INTO persons (first_name, last_name)
VALUES ('Вово', 'Тряпкин'),
       ('Миша', 'Лужин'),
       ('Иван', 'Лапшин');

INSERT INTO telephone_books (person_id)
VALUES (1),
       (2),
       (3);

INSERT INTO notes (telephone_book_id, contact_name, telephone_number)
VALUES (1, 'Егор', '+79546982514'),
       (1, 'Светалана', '+79546982515'),
       (2, 'Дед', '+79816910510'),
       (2, 'Коля', '+79817126598'),
       (3, 'Степа', '+79312567866'),
       (3, 'Лена', '+79213659845');