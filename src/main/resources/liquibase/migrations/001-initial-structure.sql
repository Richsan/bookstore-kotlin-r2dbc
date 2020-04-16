CREATE TABLE book_language
(
    id varchar(10) NOT NULL PRIMARY KEY,
    description character varying NOT NULL
);

CREATE TABLE book_author
(
    id bigserial NOT NULL PRIMARY KEY,
    citation_name  varchar(100) NOT NULL,
    name varchar(300),
    biography character varying NOT NULL
);

CREATE TABLE book_publisher
(
    id bigserial NOT NULL PRIMARY KEY,
    name varchar(100) NOT NULL
);

CREATE TABLE book
(
    id bigserial NOT NULL PRIMARY KEY,
    title varchar(300) NOT NULL,
    price bigint NOT NULL,
    available_qty int NOT NULL,
    release_date date NOT NULL,
    publisher_id bigint NOT NULL REFERENCES book_publisher(id) ON DELETE CASCADE
);

CREATE TABLE book_author_relationship
(
    book_id bigint NOT NULL REFERENCES book(id) ON DELETE CASCADE,
    author_id bigint NOT NULL REFERENCES book_author(id) ON DELETE CASCADE,
    PRIMARY KEY(book_id, author_id)
);

CREATE TABLE book_language_relationship
(
    book_id bigint NOT NULL REFERENCES book(id) ON DELETE CASCADE,
    language_id varchar(10) NOT NULL REFERENCES book_language(id) ON DELETE CASCADE,
    PRIMARY KEY(book_id, language_id)

);


--rollback drop table if exists book_author_relationship, book_publisher_relationship, book_language_relationship, book_language, book_author, book_publisher, book;