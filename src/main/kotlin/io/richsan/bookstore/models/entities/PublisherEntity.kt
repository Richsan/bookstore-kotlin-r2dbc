package io.richsan.bookstore.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table


@Table("book_publisher")
data class PublisherEntity (
        @Id
        val id : Long? = null,
        val name  : String = ""
)