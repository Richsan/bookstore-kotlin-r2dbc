package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.BookEntity
import org.springframework.data.repository.reactive.ReactiveCrudRepository


interface BookRepository : ReactiveCrudRepository<BookEntity, Long>