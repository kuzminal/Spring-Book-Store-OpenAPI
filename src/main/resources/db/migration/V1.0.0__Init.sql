create schema if not exists bookstore;

create TABLE IF NOT EXISTS bookstore.genres
(
    id   bigserial   NOT NULL,
    name varchar(56) NOT NULL,
    PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS bookstore.books
(
    id          bigserial                NOT NULL,
    title       varchar(56)              NOT NULL,
    isbn        varchar(12)              NOT NULL,
    annotation  varchar(3000),
    image_cover bytea,
    price       numeric(16, 4) DEFAULT 0 NOT NULL,
    count       numeric(8, 0)  DEFAULT 0 NOT NULL,
    genre_id    bigint                   NOT NULL UNIQUE,
    FOREIGN KEY (genre_id)
    REFERENCES bookstore.genres (id),
    PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS bookstore.authors
(
    id        bigserial   NOT NULL,
    name      varchar(56) NOT NULL,
    first_name varchar(200),
    last_name  varchar(200),
    email     varchar(200),
    PRIMARY KEY (id)
    );

create TABLE IF NOT EXISTS bookstore.authors_books
(
    authors_id int NOT NULL,
    books_id   int NOT NULL
);

insert into bookstore.genres
values (1, 'Sci-Fi');

insert into bookstore.authors
values (1, 'Jack London',
        'Jack',
        'London',
        'London@jack.com');

insert into bookstore.books
values (1, 'Antifragile',
        '123456789012',
        'Antifragile - Things that gains from disorder. By Nassim Nicholas Taleb',
        null,
        17.15,
        33,
        1);

insert into bookstore.authors_books
values (1,
        1);