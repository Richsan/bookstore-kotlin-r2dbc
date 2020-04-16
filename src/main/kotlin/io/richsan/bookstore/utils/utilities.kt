package io.richsan.bookstore.utils

import reactor.core.publisher.Mono
import reactor.util.context.Context

fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun Mono<Context>.putIntoContext(key : String, obj : Mono<out Any>) : Mono<Context> {
    return this.zipWith(obj)
            .map { it.t1.put(key,it.t2) }
}