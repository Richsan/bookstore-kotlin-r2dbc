package io.richsan.bookstore.utils.validators

import io.richsan.bookstore.models.requests.Violation
import io.richsan.bookstore.utils.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.time.temporal.Temporal

fun List<Any>.notEmptyValidator(fieldName : String) : Mono<Violation> = this.toMono()
        .filter { it.isEmpty() }
        .map { Violation(fieldName,"Cannot be an empty list") }

fun Number.maxValidator(fieldName: String, maxNumber : Number) : Mono<Violation> = this.toMono()
        .filter { it.compareTo(maxNumber) > 0 }
        .map { Violation(fieldName, "Cannot be bigger than ${maxNumber}") }

fun Number.minValidator(fieldName: String, minNumber : Number) : Mono<Violation> = this.toMono()
        .filter { it.compareTo(minNumber) < 0 }
        .map { Violation(fieldName, "Cannot be lower than ${minNumber}") }

fun String.maxSizeValidator(fieldName: String, maxSize : Number) : Mono<Violation> = this.toMono()
        .filter { it.length > maxSize }
        .map { Violation(fieldName, "Cannot has more than ${maxSize} characters") }

fun String.minSizeValidator(fieldName: String, minSize : Number) : Mono<Violation> = this.toMono()
        .filter { it.length < minSize }
        .map { Violation(fieldName, "Cannot has less than ${minSize} characters")}

fun String.nonEmptyValidator(fieldName: String) : Mono<Violation> = this.toMono()
        .filter { it.isEmpty() }
        .map { Violation(fieldName, "Cannot be empty string") }

fun String.alphabeticValidator(fieldName: String) : Mono<Violation> = this.toMono()
        .filter { ! it.isOnlyLetters() }
        .map { Violation(fieldName, "Only letters is allowed") }

fun String.digitValidator(fieldName: String) : Mono<Violation> = this.toMono()
        .filter { ! it.isOnlyDigits() }
        .map { Violation(fieldName, "Only digits is allowed") }

fun Temporal.futureValidator(fieldName: String) : Mono<Violation>
        = this.toMono()
        .filter {it.inPresentOrPast() }
        .map { Violation(fieldName, "This should be in the future") }

fun Temporal.futureOrPresentValidator(fieldName: String) : Mono<Violation>
        = this.toMono()
        .filter {it.inPast() }
        .map { Violation(fieldName, "This should be in the present or in the future") }

fun Temporal.pastValidator(fieldName: String) : Mono<Violation>
        = this.toMono()
        .filter {it.inPresentOrFuture() }
        .map { Violation(fieldName, "This should be in the past") }

fun Temporal.presentOrPastValidator(fieldName: String) : Mono<Violation>
        = this.toMono()
        .filter {it.inFuture() }
        .map { Violation(fieldName, "This should be in the present or in the past") }