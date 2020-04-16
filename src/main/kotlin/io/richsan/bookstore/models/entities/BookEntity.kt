package io.richsan.bookstore.models.entities

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.util.*

const val tableName = "book"

@Table(tableName)
data class BookEntity(
        @Id
        val id : Long? = null,
        val title : String,
        val price : Long,
        val avialableQty : Int,
        val releaseDate : LocalDate,
        val authors: List<AuthorEntity>,
        val languages : List<LanguageEntity>,
        val publisher : PublisherEntity
)