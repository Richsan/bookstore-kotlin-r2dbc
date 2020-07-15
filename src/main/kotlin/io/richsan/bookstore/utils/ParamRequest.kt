package io.richsan.bookstore.utils

import io.richsan.bookstore.models.requests.Violation
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import kotlin.reflect.KFunction1

data class ParamRequest(val required: Boolean = false,
                        val violations: List<KFunction1<String, Flux<Violation>>> = listOf()) {
    fun validate(value : String) : Flux<Violation> {
        return this.violations.toFlux()
                .flatMap { it(value) }
    }

    fun requiredValidation(fieldName : String) : Mono<Violation> {
        return if(required)
            Mono.just(Violation(fieldName, "Required field missing"))
        else
            Mono.empty()
    }
}