package io.richsan.bookstore.models.entities

import org.springframework.data.relational.core.mapping.Table


@Table("book_publisher")
data class PublisherEntity (
        val id : Long? = null,
        val name  : String
)