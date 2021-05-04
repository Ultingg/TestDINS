

use dins;
drop table if exists notes;
drop table if exists telephone_books;
drop table if exists persons;


create table persons
(
    person_id integer auto_increment primary key,
    first_name varchar(100),
    last_name varchar(100)
);

create table telephone_books
(
    telephone_book_id integer auto_increment primary key,
    person_id integer not null,
    foreign key (person_id) references persons(person_id)
);

create table notes
(
    note_id serial auto_increment primary key,
    telephone_book_id integer not null,
    contact_name varchar(100),
    telephone_number varchar(12) unique,
    foreign key (telephone_book_id) references telephone_books(telephone_book_id)
);



