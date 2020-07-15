package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.*
import io.richsan.bookstore.models.responses.AuthorResponse
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.models.responses.LanguageResponse
import io.richsan.bookstore.models.responses.PublisherResponse
import io.richsan.bookstore.services.BookstoreService
import io.richsan.bookstore.utils.postRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@Service
class BookstoreController(
        val bookstoreService: BookstoreService
) {
    fun insertBook(serverRequest: ServerRequest, requestBody : BookRequest)
            : Mono<BookResponse> = bookstoreService.insertABook(requestBody)

    fun insertAuthor(serverRequest: ServerRequest, requestBody: AuthorRequest)
            : Mono<AuthorResponse> = bookstoreService.insertAnAuthor(requestBody)

    fun insertPublisher(serverRequest: ServerRequest, requestBody: PublisherRequest)
            : Mono<PublisherResponse> = bookstoreService.insertAPublisher(requestBody)

    fun insertLanguage(serverRequest: ServerRequest, requestBody: LanguageRequest)
            : Mono<LanguageResponse> = bookstoreService.insertALanguage(requestBody)

}