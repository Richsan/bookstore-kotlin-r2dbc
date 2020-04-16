package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.AuthorRequest
import io.richsan.bookstore.models.requests.Bookrequest
import io.richsan.bookstore.models.requests.LanguageRequest
import io.richsan.bookstore.models.requests.PublisherRequest
import io.richsan.bookstore.models.responses.AuthorResponse
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.models.responses.LanguageResponse
import io.richsan.bookstore.models.responses.PublisherResponse
import io.richsan.bookstore.services.BookstoreService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class BookstoreController(
        val bookstoreService: BookstoreService
) {

    @PostMapping("/books")
    fun insertBook(@RequestBody request : Bookrequest) : Mono<BookResponse> = bookstoreService.insertABook(request)

    @PostMapping("/authors")
    fun insertAuthor(@RequestBody request: AuthorRequest) : Mono<AuthorResponse> = bookstoreService.insertAnAuthor(request)

    @PostMapping("/publishers")
    fun insertPublisher(@RequestBody request: PublisherRequest) : Mono<PublisherResponse> = bookstoreService.insertAPublisher(request)

    @PostMapping("/authors")
    fun insertLanguage(@RequestBody request: LanguageRequest) : Mono<LanguageResponse> = bookstoreService.insertALanguage(request)

}