package io.richsan.bookstore.controllers

import io.richsan.bookstore.models.requests.*
import io.richsan.bookstore.utils.ParamRequest
import io.richsan.bookstore.utils.postRequest
import io.richsan.bookstore.utils.putRequest
import io.richsan.bookstore.utils.validators.digitValidator
import io.richsan.bookstore.utils.validators.nonEmptyValidator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.net.URI
import javax.xml.validation.Validator
import kotlin.reflect.KFunction1

fun String.digitQueryValidator() : Flux<Violation> {
    return Flux.from(this.digitValidator("name"))
}
@Configuration
class BookstoreRouter {

    @Bean
    fun routes(bookstoreController: BookstoreController) = router {
        GET("/") {
            ServerResponse.temporaryRedirect(URI("/doc/swagger-ui.html")).build()
        }
        ("/api" and accept(MediaType.APPLICATION_JSON)) .nest {

            POST("/authors", postRequest(
                    requestClass = AuthorRequest::class,
                    handler = bookstoreController::insertAuthor
            ))
            POST("/publishers", postRequest(
                    requestClass = PublisherRequest::class,
                    handler = bookstoreController::insertPublisher))

            PUT("/languages/{id}", putRequest(
                    requestClass = LanguageRequest::class,
                    bodyViolations = LanguageRequest::validate,
                    handler = bookstoreController::insertLanguage))
        }
    }
}