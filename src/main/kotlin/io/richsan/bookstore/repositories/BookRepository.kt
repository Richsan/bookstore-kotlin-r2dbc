package io.richsan.bookstore.repositories

import io.richsan.bookstore.models.entities.BookEntity
import io.richsan.bookstore.models.entities.PublisherEntity
import io.richsan.bookstore.models.entities.relationships.BookAuthorRelationship
import io.richsan.bookstore.models.entities.relationships.BookLanguageRelationship
import io.richsan.bookstore.models.entities.tableName
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import java.time.LocalDate


@Service
class BookRepository {

    @Autowired
    lateinit var databaseClient : DatabaseClient

    @Transactional
    fun save(bookEntity: BookEntity) : Mono<BookEntity> {

        return databaseClient.insert().into(tableName)
                .value("title", bookEntity.title)
                .value("price", bookEntity.price)
                .value("available_qty", bookEntity.availableQty)
                .value("release_date",bookEntity.releaseDate)
                .value("publisher_id", bookEntity.publisher.id!!)
                .fetch()
                .all()
                .map { it["id"] as Long}
                .map { bookEntity.copy(id = it) }
                .flatMap { saveRelationships(it) }
                .single()
    }

    fun getABook(bookId : Long) : Mono<BookEntity>  {
        return databaseClient.select().from(tableName)
                .project("id", "title", "price",
                        "available_qty", "release_date", "publisher_id")
                .matching(Criteria.where("id").`is`(bookId))
                .fetch()
                .one()
                .map { BookEntity(id = it["id"] as Long,
                        availableQty = it["available_qty"] as Int,
                        price = it["price"] as Long,
                publisher = PublisherEntity(id = it["publisher_id"] as Long),
                title = it["title"] as String,
                releaseDate = it["release_date"] as LocalDate) }
    }

    private fun saveRelationships(bookEntity: BookEntity) : Mono<BookEntity> {
        val bookAuthorRelations = Mono.just(bookEntity)
                .flatMapIterable { it.authors }
                .map { it.id }
                .map { BookAuthorRelationship(bookId = bookEntity.id!!, authorId = it!!) }

        val bookLanguageRelations = Mono.just(bookEntity)
                .flatMapIterable { it.languages }
                .map { it.id }
                .map { BookLanguageRelationship(bookId = bookEntity.id!!, languageId = it!!) }

        return databaseClient.insert().into(BookAuthorRelationship::class.java)
                .using(bookAuthorRelations)
                .then()
                .then(databaseClient
                        .insert()
                        .into(BookLanguageRelationship::class.java)
                        .using(bookLanguageRelations)
                        .then())
                .thenReturn(bookEntity)
    }

}