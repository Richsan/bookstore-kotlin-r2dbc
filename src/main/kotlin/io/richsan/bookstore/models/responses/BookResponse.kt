package io.richsan.bookstore.models.responses

import org.springframework.data.annotation.Id
import java.time.LocalDate

data class BookResponse (
        val id : Long,
        val title : String,
        val price : Long,
        val availableQty : Int,
        val releaseDate : LocalDate,
        val publisher : PublisherResponse,
        val authors : List<AuthorResponse>,
        val languages : List<LanguageResponse>
)

data class AuthorResponse (
        val id : Long,
        val name : String,
        val citationName : String,
        val biography : String
)

data class PublisherResponse (
        val id : Long,
        val name  : String
)

data class LanguageResponse (
        val id : String ,
        val description: String
)