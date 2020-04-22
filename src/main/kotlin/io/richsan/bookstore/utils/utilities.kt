package io.richsan.bookstore.utils

import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono
import reactor.util.context.Context
import kotlin.reflect.KClass


fun String.capitalizeWords(): String = split(" ").map { it.capitalize() }.joinToString(" ")

fun Mono<Context>.putIntoContext(key : String, obj : Mono<out Any>) : Mono<Context> {
    return this.zipWith(obj)
            .map { it.t1.put(key,it.t2) }
}

fun postRequest(requestClass : KClass<out Any>,handler : (m: Any) -> Mono<out Any>) : (ServerRequest) -> Mono<ServerResponse> {
    return { request : ServerRequest ->
        request.bodyToMono(requestClass.java)
                .flatMap { handler(it) }
                .flatMap { ServerResponse.created(request.uri()).bodyValue(it) }
    }
}


