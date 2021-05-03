#


# # drop table if exists notes;
# # drop table if exists book_note;
# #
# use dins;
# drop table if exists notes;
# drop table if exists telephone_books;
# drop table if exists persons;
#
#
# create table dins.persons
# (
#     id integer auto_increment primary key,
#     first_name varchar(100),
#     last_name varchar(100)
# );
#
#
# create table dins.telephone_books
# (
#
#     person integer primary key references dins.persons(id),
#     book_name varchar(255),
#     pages varchar(100)
# );
#
#
#
# create table notes
# (
#     id serial primary key,
#     telephone_book integer references telephone_books(person),
#     contact_name varchar(100),
#     telephone_number varchar(12)
# );

use dins;
drop table if exists notes;
drop table if exists telephone_books;
drop table if exists persons;


create table dins.persons
(
    person_id integer auto_increment primary key,
    first_name varchar(100),
    last_name varchar(100)
);


create table dins.telephone_books
(
    telephone_book_id integer auto_increment primary key,
    person_id integer not null,
    foreign key (person_id) references dins.persons(person_id)
);



create table notes
(
    note_id serial auto_increment primary key,
    telephone_book_id integer not null ,
    contact_name varchar(100),
    telephone_number varchar(12),
    foreign key (telephone_book_id) references telephone_books(telephone_book_id)
);


drop table if exists record;
drop table if exists record_package;

create table record_package
(
    record_package_id integer auto_increment       not null primary key,
    name              varchar(256) not null
);

create table record
(
    record_id         serial       not null primary key,
    record_package_id integer       not null,
    data              varchar(256) not null,
    foreign key (record_package_id) references record_package(record_package_id)
);


# drop table if exists parts;
# drop table if exists cars;
#
# create table cars
# (
#     car_id integer auto_increment primary key,
#     color  varchar(255),
#     age    integer
#
# );
#
# create table parts
# (
#     id        serial primary key,
#     car       integer references cars (car_id),
#     item_name varchar(100),
#     price     integer
# );
# #



