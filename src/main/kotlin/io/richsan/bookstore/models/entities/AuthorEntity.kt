package io.richsan.bookstore.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


/*CREATE TABLE book_author
(
id bigserial NOT NULL PRIMARY KEY,
citation_name  varchar(100) NOT NULL,
name varchar(300),
biography character varying NOT NULL
);*/
@Table("book_author")
data class AuthorEntity(
        @Id
        val id : Long,
        val name : String,
        val citationName : String,
        val biography : String
)
