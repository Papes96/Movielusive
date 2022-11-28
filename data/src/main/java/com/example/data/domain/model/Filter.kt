package com.example.data.domain.model

data class Filter(
    var page: Int = 1,
    val query: String? = null,
    val genres: Genre? = null,
    val sortBy: String? = null,
    val withCast: Cast? = null
)
