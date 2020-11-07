package io.richsan.bookstore.models.requests

import io.richsan.bookstore.utils.validators.*
import reactor.core.publisher.Flux
import reactor.kotlin.core.publisher.toFlux
import java.time.LocalDate

data class BookRequest (
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
        val description: String
)

data class Violation(
        val field : String,
        val error: String
)

fun BookRequest.validate() : Flux<Violation> = Flux.from(this.authors.notEmptyValidator("authors"))
        .concatWith(this.languages.notEmptyValidator("languages"))
        .concatWith(this.authors.toFlux().flatMap { it.minValidator("author-item",0) })
        .concatWith(this.languages.toFlux().flatMap { it.nonEmptyValidator("language-item") })
        .concatWith(this.publisher.minValidator("publisher", 0))
        .concatWith(this.availableQty.minValidator("availableQty", 0))
        .concatWith(this.title.nonEmptyValidator("title"))
        .concatWith(this.title.maxSizeValidator("title", 100))
        .concatWith(this.price.minValidator("price", 0))
        .concatWith(this.releaseDate.presentOrPastValidator("releaseDate"))

fun LanguageRequest.validate() : Flux<Violation> = Flux.from(
        this.description.nonEmptyValidator("description"))