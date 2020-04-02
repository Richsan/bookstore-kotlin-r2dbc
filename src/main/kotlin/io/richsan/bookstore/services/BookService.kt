package io.richsan.bookstore.services

import io.richsan.bookstore.adapters.toEntity
import io.richsan.bookstore.adapters.toResponse
import io.richsan.bookstore.models.requests.Bookrequest
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.repositories.BookRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BookService(
        val bookRepository : BookRepository
) {

    fun insertBook(request : Bookrequest) : Mono<BookResponse> =
            Mono.just(request)
                .map { it.toEntity() }
                .flatMap { bookRepository.save(it) }
                .map { it.toResponse() }

}