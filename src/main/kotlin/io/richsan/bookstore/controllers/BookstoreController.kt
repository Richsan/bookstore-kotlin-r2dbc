package io.richsan.bookstore.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api")
class BookstoreController {

    @GetMapping("/hello")
    fun helloWord() : Mono<String> = Mono.just("Hello World")

}