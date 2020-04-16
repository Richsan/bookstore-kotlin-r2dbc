package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.PublisherEntity
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface PublisherRepository : ReactiveCrudRepository<PublisherEntity, Long>