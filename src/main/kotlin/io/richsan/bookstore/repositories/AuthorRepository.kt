package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.AuthorEntity
import io.richsan.bookstore.models.entities.BookEntity
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.r2dbc.query.Criteria.*
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface AuthorRepository : ReactiveCrudRepository<AuthorEntity,Long>
