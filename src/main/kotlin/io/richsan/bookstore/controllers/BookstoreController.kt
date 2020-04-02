package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.Bookrequest
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.services.BookService
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class BookstoreController(
        val bookService: BookService
) {

    @PostMapping("/book")
    fun insertBook(@RequestBody request : Bookrequest) : Mono<BookResponse> = bookService.insertBook(request)

}