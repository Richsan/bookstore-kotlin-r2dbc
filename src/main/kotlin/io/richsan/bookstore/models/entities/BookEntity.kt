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
        val availableQty : Int,
        val releaseDate : LocalDate,
        @Transient
        val authors: List<AuthorEntity> = listOf(),
        @Transient
        val languages : List<LanguageEntity> = listOf(),
        @Transient
        val publisher : PublisherEntity
)