package com.example.data.domain.model

import androidx.recyclerview.widget.DiffUtil

data class Movie (
    val id: Int? = null,
    val title: String? = null,
    val overview: String? = null,
    val poster: String? = null,
    val backdrop: String? = null,
    val release: String? = null,
    val genres: List<Genre>? = null,
    val voteAverage: String? = null,
    val tagline: String? = null,
    val collection: Collection? = null
)

object MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Movie, newItem: Movie) = oldItem == newItem
}