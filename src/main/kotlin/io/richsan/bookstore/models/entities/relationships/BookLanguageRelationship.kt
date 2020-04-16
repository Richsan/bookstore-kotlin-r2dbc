package io.richsan.bookstore.models.entities.relationships

import org.springframework.data.relational.core.mapping.Table

@Table("book_language_relationship")
data class BookLanguageRelationship (
    val bookId : Long,
    val languageId : String
)