package io.richsan.bookstore.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("book_language")
data class LanguageEntity (
        @Id
        val id : String ,
        val description: String
)