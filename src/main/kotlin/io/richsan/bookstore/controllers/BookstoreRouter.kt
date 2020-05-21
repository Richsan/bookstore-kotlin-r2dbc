package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.*
import io.richsan.bookstore.utils.postRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class BookstoreRouter {

    @Bean
    fun routes(bookstoreController: BookstoreController) = router {
        ("/api" and accept(MediaType.APPLICATION_JSON)) .nest {
            POST("/books", postRequest(
                    requestClass = BookRequest::class,
                    violations = BookRequest::validate,
                    handler = bookstoreController::insertBook))

            POST("/authors", postRequest(
                    requestClass = AuthorRequest::class,
                    handler = bookstoreController::insertAuthor
            ))
            POST("/publisher", postRequest(
                    requestClass = PublisherRequest::class,
                    handler = bookstoreController::insertPublisher))

            POST("/languages", postRequest(
                    requestClass = LanguageRequest::class,
                    handler = bookstoreController::insertLanguage))
        }
    }
}