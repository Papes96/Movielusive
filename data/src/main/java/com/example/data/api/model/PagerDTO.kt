package com.example.data.api.model

import com.google.gson.annotations.SerializedName

data class PagerDTO(
    val page: Int,
    val results: List<MovieDTO>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)
