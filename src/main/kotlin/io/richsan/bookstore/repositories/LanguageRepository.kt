package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.LanguageEntity
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.r2dbc.query.Criteria.*
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface LanguageRepository : ReactiveCrudRepository<LanguageEntity, String>