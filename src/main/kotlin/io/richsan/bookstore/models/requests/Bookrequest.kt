package io.richsan.bookstore.models.requests

import java.time.LocalDate

data class Bookrequest (
        val title : String,
        val price : Long,
        val availableQty : Int,
        val releaseDate : LocalDate,
        val publisher : Long,
        val authors : List<Long>,
        val languages : List<String>
)

data class AuthorRequest (
        val name : String,
        val citationName : String,
        val biography : String
)

data class PublisherRequest (
        val name  : String
)

data class LanguageRequest (
        val id: String,
        val description: String
)