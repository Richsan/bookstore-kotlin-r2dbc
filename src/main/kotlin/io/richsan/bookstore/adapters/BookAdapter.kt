package io.richsan.bookstore.adapters

import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.requests.Bookrequest
import io.richsan.bookstore.models.responses.BookResponse

fun Bookrequest.toEntity() : BookEntity = BookEntity(
        title = title,
        price = price
)

fun BookEntity.toResponse() : BookResponse = BookResponse(
        id = id!!,
        title = title,
        price = price
)