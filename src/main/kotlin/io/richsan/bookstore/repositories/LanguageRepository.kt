package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.LanguageEntity
import io.richsan.bookstore.models.entities.relationships.BookLanguageRelationship
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.r2dbc.query.Criteria.*
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class LanguageRepository {

    @Autowired
    lateinit var databaseClient : DatabaseClient

    @Transactional
    fun save(languageEntity: LanguageEntity) : Mono<LanguageEntity> {
        return databaseClient.insert().into(LanguageEntity::class.java)
                .using(languageEntity)
                .then()
                .thenReturn(languageEntity)
    }

    fun findAllById(languageIds : List<String>) : Flux<LanguageEntity> {
        return databaseClient.select().from(LanguageEntity::class.java)
                .matching(where("id").`in`(languageIds))
                .`as`(LanguageEntity::class.java)
                .all()
    }

    fun findAllByBookId(bookId: Long) : Flux<LanguageEntity> {
        return databaseClient.select().from(BookLanguageRelationship::class.java)
                .matching(where("book_id").`is`(bookId))
                .fetch()
                .all()
                .map { it.languageId }
                .collectList()
                .flatMapMany { findAllById(it) }
    }
}