package io.richsan.bookstore.utils

import com.fasterxml.jackson.databind.ObjectMapper
import io.richsan.bookstore.models.requests.Violation
import org.springframework.data.relational.core.sql.In
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import reactor.util.context.Context
import java.math.BigDecimal
import java.math.BigInteger
import java.time.*
import java.time.temporal.Temporal
import java.time.temporal.TemporalAccessor
import java.util.*
import java.util.concurrent.Callable
import java.util.function.Function
import javax.xml.validation.Validator
import kotlin.reflect.KClass
import kotlin.reflect.KFunction


fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun Mono<Context>.putIntoContext(key : String, obj : Mono<out Any>) : Mono<Context> {
    return this.zipWith(obj)
            .map { it.t1.put(key,it.t2) }
}


private val objectMapper = ObjectMapper()


fun postRequest(requestClass : KClass<out Any>,
                violations : KFunction<Flux<Violation>>? = null,
                handler : KFunction<Mono<out Any>>) : (ServerRequest) -> Mono<ServerResponse> {
    return { request : ServerRequest ->

        request.bodyToMono(requestClass.java)
                .flatMap {
                    val successfulResponse  = it.toMono()
                            .flatMap { handler.call(it) }
                            .flatMap { ServerResponse.created(request.uri()).bodyValue(it)}



                    Flux.from(violations?.call(it) ?: Mono.empty())
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
        is Date -> Date.from(Instant.now()).equals(this)
        is Calendar -> Calendar.getInstance().equals(this)
        is Instant -> Instant.now().equals(this)
        is ZonedDateTime -> Instant.now().equals(this.toInstant())
        is LocalDateTime -> LocalDateTime.now().equals(this)
        is LocalTime -> LocalTime.now().equals(this)
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

