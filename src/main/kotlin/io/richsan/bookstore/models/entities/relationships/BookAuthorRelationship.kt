package io.richsan.bookstore.models.entities.relationships

import org.springframework.data.relational.core.mapping.Table

@Table("book_author_relationship")
data class BookAuthorRelationship (
        val bookId : Long,
        val authorId: Long
)