package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.BookRequest
import io.richsan.bookstore.models.requests.validate
import io.richsan.bookstore.utils.getRequest
import io.richsan.bookstore.utils.postRequest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router

@Configuration
class BookRouter {

    @Bean
    fun bookRoutes(bookstoreController: BookstoreController) = router {
        ("/api" and accept(MediaType.APPLICATION_JSON)) .nest {
            POST("/books", postRequest(
                    requestClass = BookRequest::class,
                    bodyViolations = BookRequest::validate,
                    handler = bookstoreController::insertBook))

            GET("/books/{bookId}", getRequest(
                    handler = bookstoreController::getABook
            ))
        }
    }
}