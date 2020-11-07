package io.richsan.bookstore.utils


import org.springframework.http.HttpStatus
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono

import java.net.URI


data class ResponseObj<T> (
        val status : HttpStatus,
        val body : T? = null,
        val location : URI? = null)

fun ResponseObj<out Any>.toResponse(request : ServerRequest) : Mono<ServerResponse> {
    var response = ServerResponse.status(this.status)
    if(this.location != null) {
        val location = request.uriBuilder()
                .path(this.location.toString())
                .build()
        response = response.location(location)
    } else if(HttpStatus.CREATED == this.status) {
        response = response.location(request.uri())
    }

    return if(this.body == null) response.build()
            else response.bodyValue(this.body)
}

fun<T> postResponse(status: HttpStatus = HttpStatus.CREATED,
                 resource: String? = null,
                 body: T? = null) : ResponseObj<T> {

    return ResponseObj(status = status,
            body = body,
            location = if(resource != null) URI("/$resource") else null)
}

fun<T> putResponse(resourceCreated : Boolean = false,
                 body: T? = null) : ResponseObj<T> {

    val status = if(resourceCreated) HttpStatus.CREATED else HttpStatus.OK

    return ResponseObj(status = status,
            body = body)
}

fun<T> getResponse(body: T? = null) : ResponseObj<T> {

    return ResponseObj(status = HttpStatus.OK,
            body = body)
}

fun<T> deleteResponse(body: T? = null) : ResponseObj<T> {

    return ResponseObj(status = HttpStatus.OK,
            body = body)
}

fun<T> patchResponse(body: T? = null) : ResponseObj<T> {

    return ResponseObj(status = HttpStatus.OK,
            body = body)
}


