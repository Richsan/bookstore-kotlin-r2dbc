package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.AuthorRequest
import io.richsan.bookstore.models.requests.BookRequest
import io.richsan.bookstore.models.requests.LanguageRequest
import io.richsan.bookstore.models.requests.PublisherRequest
import io.richsan.bookstore.models.responses.AuthorResponse
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.models.responses.LanguageResponse
import io.richsan.bookstore.models.responses.PublisherResponse
import io.richsan.bookstore.services.BookstoreService
import io.richsan.bookstore.utils.postRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.*
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Mono


@Configuration
//@EnableWebFlux
class BookstoreHandler(
        val bookstoreService: BookstoreService
) {


    fun insertBook(request : BookRequest) : Mono<BookResponse> = bookstoreService.insertABook(request)

    fun insertAuthor(request: AuthorRequest) : Mono<AuthorResponse> = bookstoreService.insertAnAuthor(request)

    fun insertPublisher(request: PublisherRequest) : Mono<PublisherResponse> = bookstoreService.insertAPublisher(request)

    fun insertLanguage(request: LanguageRequest) : Mono<LanguageResponse> = bookstoreService.insertALanguage(request)

    @Bean
    fun routes() = router {
        ("/api" and accept(APPLICATION_JSON)) .nest {
            POST("/books", postRequest(BookRequest::class) {insertBook(it as BookRequest)})
            POST("/authors", postRequest(AuthorRequest::class) { insertAuthor(it as AuthorRequest) })
            POST("/publisher", postRequest(PublisherRequest::class) { insertPublisher(it as PublisherRequest) })
            POST("/languages", postRequest(LanguageRequest::class) { insertLanguage(it as LanguageRequest) })
        }
    }

}