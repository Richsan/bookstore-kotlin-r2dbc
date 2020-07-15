package io.richsan.bookstore.utils

import io.richsan.bookstore.models.requests.Violation
import org.springframework.http.HttpStatus
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toFlux
import reactor.kotlin.core.publisher.toMono
import reactor.util.context.Context
import java.math.BigDecimal
import java.math.BigInteger
import java.time.*
import java.time.temporal.Temporal
import java.util.*
import java.util.function.Function
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.jvm.reflect


fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun Mono<Context>.putIntoContext(key : String, obj : Mono<out Any>) : Mono<Context> {
    return this.zipWith(obj)
            .map { it.t1.put(key,it.t2) }
}


private fun KFunction<Flux<Violation>>?.executeAndGetViolations(value : Any) : Flux<Violation> =
        this?.call(value) ?: Flux.from(Mono.empty())



private fun Flux<Map.Entry<String,
        ParamRequest>>.getQueryViolations(queryParams : MultiValueMap<String, String>)
        : Flux<Violation> =
        this.flatMap {
            queryParams[it.key]?.let { paramValueList ->
                paramValueList.toFlux()
                        .flatMap { value ->
                    it.value.validate(value)
                }
            } ?: it.value.requiredValidation(it.key)
        }

private fun Flux<Map.Entry<String,
        ParamRequest>>.getPathViolations(pathParams : Map<String, String>)
        : Flux<Violation> =
        this.flatMap {
            pathParams[it.key]?.let { value ->
                it.value.validate(value)
            } ?: it.value.validate(it.key)
        }

private fun Flux<Violation>.getMapErrorResponse(mapKey : String)
        : Mono<Map<String, List<Violation>>> = this
        .collectList()
        .filter{it.isNotEmpty()}
        .map { mapOf(mapKey to it) }

fun postRequest(requestClass : KClass<out Any>,
                bodyViolations : KFunction<Flux<Violation>>? = null,
                queryViolations : Map<String,ParamRequest>? = null,
                pathViolations : Map<String,ParamRequest>? = null,
                handler : KFunction<Mono<out Any>>,
                status: HttpStatus = HttpStatus.CREATED) : (ServerRequest) -> Mono<ServerResponse> {
    return { request : ServerRequest ->

        request.bodyToMono(requestClass.java)
                .flatMap {
                    val successfulResponse  = it.toMono()
                            .flatMap { handler.call(request,it) }
                            .flatMap { ServerResponse.status(status)
                                    .location(request.uri())
                                    .bodyValue(it)}

                    val queryViolationsFlux : Mono<Map<String,List<Violation>>> = queryViolations?.run {
                        entries
                                .toFlux()
                                .getQueryViolations(request.queryParams())
                                .getMapErrorResponse("queryParams")
                    } ?:  Mono.empty()

                    val pathViolationsFlux : Mono<Map<String,List<Violation>>> = pathViolations?.run {
                        entries
                                .toFlux()
                                .getPathViolations(request.pathVariables())
                                .getMapErrorResponse("pathParams")
                    } ?:  Mono.empty()

                    val bodyViolationsFlux  = bodyViolations.executeAndGetViolations(it)
                            .getMapErrorResponse("body")

                    Flux.from(queryViolationsFlux)
                            .concatWith(pathViolationsFlux)
                            .concatWith(bodyViolationsFlux)
                            .collectList()
                            .filter { it.isNotEmpty() }
                            .flatMap { ServerResponse.badRequest().bodyValue(it)}
                            .switchIfEmpty(successfulResponse)
                }
    }
}





operator fun Number.compareTo(other: Number): Int {
    return when (this) {
        is Long   -> this.toLong().compareTo(other.toLong())
        is Int    -> this.toInt().compareTo(other.toInt())
        is Short  -> this.toShort().compareTo(other.toShort())
        is Byte   -> this.toByte().compareTo(other.toByte())
        is Double -> this.toDouble().compareTo(other.toDouble())
        is Float  -> this.toFloat().compareTo( other.toFloat())
        is BigInteger -> this.compareTo(other as BigInteger)
        is BigDecimal -> this.compareTo(other as BigDecimal)
        else      -> throw RuntimeException("Unknown numeric type")
    }
}


fun String.isOnlyLetters(): Boolean = this.filter {
    it.isLetter()
}.length == this.length

fun String.isOnlyDigits(): Boolean = this.filter {
    it.isDigit()
}.length == this.length

fun String.isOnlyAlphaNumeric(): Boolean = this.filter {
    it.isLetter() || it.isDigit()
}.length == this.length


fun Temporal.inPast() : Boolean {
    return when(this) {
        is LocalDate -> LocalDate.now().isAfter(this)
        is Date -> Date.from(Instant.now()).after(this)
        is Calendar -> Calendar.getInstance().after(this)
        is Instant -> Instant.now().isAfter(this)
        is ZonedDateTime -> Instant.now().isAfter(this.toInstant())
        is LocalDateTime -> LocalDateTime.now().isAfter(this)
        is LocalTime -> LocalTime.now().isAfter(this)
        else -> throw RuntimeException("Unknown temporal type")
    }
}

fun Temporal.inPresent() : Boolean {
   return when(this) {
        is LocalDate -> LocalDate.now().isEqual(this)
        is Date -> Date.from(Instant.now()) == this
        is Calendar -> Calendar.getInstance() == this
        is Instant -> Instant.now() == this
        is ZonedDateTime -> Instant.now() == this.toInstant()
        is LocalDateTime -> LocalDateTime.now() == this
        is LocalTime -> LocalTime.now() == this
        else -> throw RuntimeException("Unknown temporal type")
    }
}

fun Temporal.inFuture() : Boolean {
    return when(this) {
        is LocalDate -> LocalDate.now().isBefore(this)
        is Date -> Date.from(Instant.now()).before(this)
        is Calendar -> Calendar.getInstance().before(this)
        is Instant -> Instant.now().isBefore(this)
        is ZonedDateTime -> Instant.now().isBefore(this.toInstant())
        is LocalDateTime -> LocalDateTime.now().isBefore(this)
        is LocalTime -> LocalTime.now().isBefore(this)
        else -> throw RuntimeException("Unknown temporal type")
    }
}

fun Temporal.inPresentOrFuture() : Boolean = !this.inPast()
fun Temporal.inPresentOrPast() : Boolean = !this.inFuture()

