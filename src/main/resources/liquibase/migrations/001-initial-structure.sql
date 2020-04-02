CREATE TABLE book
(
    id bigserial NOT NULL PRIMARY KEY,
    title character varying NOT NULL,
    price bigint NOT NULL
);

--rollback drop table book;