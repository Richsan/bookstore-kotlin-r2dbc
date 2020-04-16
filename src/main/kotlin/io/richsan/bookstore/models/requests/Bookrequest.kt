package io.richsan.bookstore.models.requests

import java.time.LocalDate

data class Bookrequest (
        val title : String,
        val price : Long,
        val availableQty : Int,
        val releaseDate : LocalDate,
        val publisher : Long,
        val authors : List<Long>,
        val languages : List<String>
)