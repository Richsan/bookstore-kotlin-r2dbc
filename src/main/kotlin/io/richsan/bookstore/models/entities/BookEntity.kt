package io.richsan.bookstore.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("book")
data class BookEntity(
        @Id
        val id : Long? = null,
        val title : String,
        val price : Long
)