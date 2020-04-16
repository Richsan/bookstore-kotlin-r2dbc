package io.richsan.bookstore.adapters

import io.richsan.bookstore.models.entities.AuthorEntity
import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.LanguageEntity
import io.richsan.bookstore.models.entities.PublisherEntity
import io.richsan.bookstore.models.requests.Bookrequest
import io.richsan.bookstore.models.responses.BookResponse
import org.reactivestreams.Publisher
import reactor.core.publisher.Mono
import reactor.util.context.Context
import java.util.concurrent.Flow


fun Context.toBookEntity() : BookEntity {
    val request : Bookrequest = this.get("request") as Bookrequest
    val authors : List<AuthorEntity> = this.get("authors") as List<AuthorEntity>
    val languages : List<LanguageEntity> = this.get("languages") as List<LanguageEntity>
    val publisher : PublisherEntity = this.get("publisher") as PublisherEntity

    return BookEntity(
            title = request.title,
            price = request.price,
            publisher = publisher,
            authors = authors,
            languages = languages,
            avialableQty = request.availableQty,
            releaseDate = request.releaseDate
    )
}

fun BookEntity.toResponse() : BookResponse = BookResponse(
        id = id!!,
        title = title,
        price = price

)

