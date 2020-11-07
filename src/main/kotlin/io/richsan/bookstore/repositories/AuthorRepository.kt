package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.AuthorEntity
import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.LanguageEntity
import io.richsan.bookstore.models.entities.relationships.BookAuthorRelationship
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.r2dbc.query.Criteria.*
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AuthorRepository {
    @Autowired
    lateinit var databaseClient : DatabaseClient

    fun save(authorEntity: AuthorEntity) : Mono<AuthorEntity> {
        return databaseClient.insert().into(AuthorEntity::class.java)
                .using(authorEntity)
                .fetch()
                .one()
                .map { it["id"] as Long}
                .map { authorEntity.copy(id = it) }
    }

    fun findAllById(authorIds : List<Long>) : Flux<AuthorEntity> {
        return databaseClient.select().from(AuthorEntity::class.java)
                .matching(where("id").`in`(authorIds))
                .fetch()
                .all()
    }

    fun findAllByBookId(bookId : Long) : Flux<AuthorEntity> {
        return databaseClient.select().from(BookAuthorRelationship::class.java)
                .matching(where("book_id").`is`(bookId))
                .fetch()
                .all()
                .map { it.authorId }
                .collectList()
                .flatMapMany { findAllById(it) }
    }
}
