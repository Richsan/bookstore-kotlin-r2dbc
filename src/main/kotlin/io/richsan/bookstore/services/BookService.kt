package io.richsan.bookstore.services

import io.richsan.bookstore.adapters.toBookEntity
import io.richsan.bookstore.adapters.toResponse
import io.richsan.bookstore.models.requests.Bookrequest
import io.richsan.bookstore.models.responses.BookResponse
import io.richsan.bookstore.repositories.AuthorRepository
import io.richsan.bookstore.repositories.BookRepository
import io.richsan.bookstore.repositories.LanguageRepository
import io.richsan.bookstore.repositories.PublisherRepository
import io.richsan.bookstore.utils.putIntoContext
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class BookService(
        val bookRepository : BookRepository,
        val authorRepository: AuthorRepository,
        val languageRepository: LanguageRepository,
        val publisherRepository: PublisherRepository
) {

    fun insertABook(request : Bookrequest) : Mono<BookResponse> {

        val languageEntities = languageRepository.findAllById(request.languages)
                .collectList()
                .filter { it.size == request.languages.size }
                .switchIfEmpty(Mono.error(RuntimeException("Language not found")))


        val authorEntities = authorRepository.findAllById(request.authors)
                .collectList()
                .filter { it.size == request.authors.size }
                .switchIfEmpty(Mono.error(RuntimeException("Author not found")))


        val publisherEntity = publisherRepository.findById(request.publisher)
                .switchIfEmpty(Mono.error(RuntimeException("No publisher found")))


        return Mono.subscriberContext()
                .putIntoContext("request", Mono.just(request))
                .putIntoContext("publisher", publisherEntity)
                .putIntoContext("authors", authorEntities)
                .putIntoContext("languages", languageEntities)
                .map { it.toBookEntity()  }
                .flatMap { bookRepository.save(it) }
                .map { it.toResponse() }
    }


}