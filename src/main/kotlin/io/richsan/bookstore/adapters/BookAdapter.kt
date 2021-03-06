package io.richsan.bookstore.adapters

import io.richsan.bookstore.models.entities.AuthorEntity
import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.LanguageEntity
import io.richsan.bookstore.models.entities.PublisherEntity
import io.richsan.bookstore.models.requests.AuthorRequest
import io.richsan.bookstore.models.requests.BookRequest
import io.richsan.bookstore.models.requests.LanguageRequest
import io.richsan.bookstore.models.requests.PublisherRequest
import io.richsan.bookstore.models.responses.AuthorResponse
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.models.responses.LanguageResponse
import io.richsan.bookstore.models.responses.PublisherResponse
import reactor.util.context.Context


fun Context.toBookEntity() : BookEntity {
    val request : BookRequest = this.get("request") as BookRequest
    val authors : List<AuthorEntity> = this.get("authors") as List<AuthorEntity>
    val languages : List<LanguageEntity> = this.get("languages") as List<LanguageEntity>
    val publisher : PublisherEntity = this.get("publisher") as PublisherEntity

    return BookEntity(
            title = request.title,
            price = request.price,
            publisher = publisher,
            authors = authors,
            languages = languages,
            availableQty = request.availableQty,
            releaseDate = request.releaseDate
    )
}

fun BookEntity.toResponse() : BookResponse = BookResponse(
        id = id!!,
        title = title,
        price = price,
        releaseDate = releaseDate,
        languages = languages.map { it.toResponse() },
        authors = authors.map { it.toResponse() },
        publisher = publisher.toResponse(),
        availableQty = availableQty

)

fun PublisherEntity.toResponse() : PublisherResponse = PublisherResponse(
        id = id!!,
        name = name
)

fun AuthorEntity.toResponse() : AuthorResponse = AuthorResponse(
        id = id!!,
        name = name,
        biography = biography,
        citationName = citationName
)

fun LanguageEntity.toResponse() : LanguageResponse = LanguageResponse(
        id = id,
        description = description
)

fun AuthorRequest.toEntity() : AuthorEntity = AuthorEntity(
        name = name,
        biography = biography,
        citationName = citationName
)

fun PublisherRequest.toEntity() : PublisherEntity = PublisherEntity(
        name = name
)

fun LanguageRequest.toEntity(id: String) : LanguageEntity = LanguageEntity(
        id = id,
        description = description
)