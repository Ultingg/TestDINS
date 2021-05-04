CREATE DATABASE IF NOT EXISTS dins;
USE dins;
DROP TABLE IF EXISTS notes;
DROP TABLE IF EXISTS  telephone_books;
DROP TABLE IF EXISTS  persons;


CREATE TABLE persons
(
    person_id  INTEGER AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100),
    last_name  VARCHAR(100)
);

CREATE TABLE  telephone_books
(
    telephone_book_id INTEGER AUTO_INCREMENT PRIMARY key,
    person_id         INTEGER NOT NULL,
    FOREIGN KEY (person_id) REFERENCES persons (person_id)
);

CREATE TABLE  notes
(
    note_id           SERIAL AUTO_INCREMENT PRIMARY key,
    telephone_book_id INTEGER NOT NULL,
    contact_name      VARCHAR(100),
    telephone_number  VARCHAR(12) UNIQUE ,
    FOREIGN KEY (telephone_book_id) REFERENCES telephone_books (telephone_book_id)
);



