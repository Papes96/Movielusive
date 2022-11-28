package com.example.data

import com.example.data.api.ServiceProvider
import com.example.data.domain.model.Filter
import com.example.data.domain.model.Genre
import com.example.data.domain.model.Movie

object Repository {
    private val service = ServiceProvider.getRetrofit()

    suspend fun getMovies(filter: Filter): MutableList<Movie>? {
        val response = if (filter.query == null) service.getDiscoverMovies(filter.page, filter.genres?.id)
        else service.getSearchMovies(filter.page, filter.query, filter.genres?.id, filter.sortBy)
        return if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.results.map { it.asDomainModel() } as MutableList<Movie>
            }
        } else null
    }

    suspend fun getMovieDetails(id: Int): Movie? {
        val response = service.getMovie(id)
        return if (response.isSuccessful) {
            response.body()?.let {
                return it.asDomainModel()
            }
        } else null
    }

    suspend fun getGenres(): List<Genre>? {
        val response = service.getGenres()
        return if (response.isSuccessful) {
            response.body()?.let { body ->
                return body.map { it.asDomainModel() }
            }
        } else null
    }
}