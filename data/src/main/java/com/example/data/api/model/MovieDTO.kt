package com.example.data.api.model

import com.example.data.domain.model.Collection
import com.example.data.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class MovieDTO(
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    @SerializedName("poster_path")
    val poster: String? = null,
    @SerializedName("backdrop_path")
    val backdrop: String? = null,
    @SerializedName("release_date")
    val release: String? = null,
    val genres: List<Genre>? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    val tagline: String? = null,
    @SerializedName("belongs_to_collection")
    val collection: CollectionDTO? = null
)