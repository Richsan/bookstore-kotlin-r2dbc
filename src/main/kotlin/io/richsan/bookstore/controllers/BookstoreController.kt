package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.*
import io.richsan.bookstore.models.responses.AuthorResponse
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.models.responses.LanguageResponse
import io.richsan.bookstore.models.responses.PublisherResponse
import io.richsan.bookstore.services.BookstoreService
import io.richsan.bookstore.utils.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@Service
class BookstoreController(
        val bookstoreService: BookstoreService
) {
    fun insertBook(serverRequest: ServerRequest, requestBody : BookRequest)
            : Mono<ResponseObj<BookResponse>> = bookstoreService.insertABook(requestBody)
                .map { postResponse(body = it,
                                    resource = it.id.toString()) }

    fun getABook(serverRequest: ServerRequest)
            : Mono<ResponseObj<BookResponse>> =
            Mono.just(serverRequest.pathVariable("bookId"))
                    .map { it.toLong() }
                    .flatMap { bookstoreService.getABook(it)}
                    .map { getResponse(body = it) }

    fun insertAuthor(serverRequest: ServerRequest, requestBody: AuthorRequest)
            : Mono<ResponseObj<AuthorResponse>> = bookstoreService.insertAnAuthor(requestBody)
                 .map { postResponse(body = it,
                                     resource = it.id.toString()) }

    fun insertPublisher(serverRequest: ServerRequest, requestBody: PublisherRequest)
            : Mono<ResponseObj<PublisherResponse>> = bookstoreService.insertAPublisher(requestBody)
                .map { postResponse(body = it,
                                    resource = it.id.toString()) }

    fun insertLanguage(serverRequest: ServerRequest, requestBody: LanguageRequest)
            : Mono<ResponseObj<LanguageResponse>> = bookstoreService.insertALanguage(serverRequest.pathVariable("id"),requestBody)
                .map { putResponse(body = it,
                        resourceCreated = true) }

}