CREATE TABLE if NOT EXISTS book
(
    id                 integer NOT NULL PRIMARY KEY AUTO_INCREMENT,
    book_name          varchar(255),
    author             varchar(255),
    isbn               varchar(255),
    price              decimal,
    quantity_on_hand   integer,
    created_date       timestamp,
    last_modified_date timestamp
);