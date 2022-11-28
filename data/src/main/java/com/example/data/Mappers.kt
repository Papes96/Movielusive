package com.example.data

import com.example.data.api.model.CollectionDTO
import com.example.data.api.model.GenreDTO
import com.example.data.api.model.MovieDTO
import com.example.data.domain.model.Collection
import com.example.data.domain.model.Genre
import com.example.data.domain.model.Movie
import kotlin.reflect.full.memberProperties

fun GenreDTO.asDomainModel() = Genre(id, name)

fun MovieDTO.asDomainModel() = with(::Movie) {
    val propertiesByName = MovieDTO::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            Movie::poster.name -> "https://image.tmdb.org/t/p/w500/$poster"
            Movie::backdrop.name -> "https://image.tmdb.org/t/p/w1280/$backdrop"
            Movie::voteAverage.name -> voteAverage.toString().substring(0, 3)
            Movie::collection.name -> collection?.asDomainModel()
            else -> propertiesByName[parameter.name]?.get(this@asDomainModel)
        }
    })
}


fun CollectionDTO.asDomainModel() = with(::Collection) {
    val propertiesByName = CollectionDTO::class.memberProperties.associateBy { it.name }
    callBy(parameters.associateWith { parameter ->
        when (parameter.name) {
            Collection::poster.name -> poster?.let{"https://image.tmdb.org/t/p/w500/$poster"}
            Collection::backdrop.name -> backdrop?.let{"https://image.tmdb.org/t/p/w1280/$backdrop"}
            else -> propertiesByName[parameter.name]?.get(this@asDomainModel)
        }
    })
}