package com.example.data.api.model

import com.google.gson.annotations.SerializedName

class CollectionDTO (
    val id: Int? = null,
    val name: String? = null,
    @SerializedName("poster_path")
    val poster: String? = null,
    @SerializedName("backdrop_path")
    val backdrop: String? = null
)