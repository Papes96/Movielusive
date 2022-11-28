package com.example.data.api.model

import androidx.recyclerview.widget.DiffUtil
import com.example.data.domain.model.Genre
import com.example.data.domain.model.Movie

data class GenreDTO(
    val id: Int,
    val name: String
)

object GenreDiffCallback : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Genre, newItem: Genre) = oldItem == newItem
}
