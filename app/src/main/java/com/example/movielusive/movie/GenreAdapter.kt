package com.example.movielusive.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.model.GenreDiffCallback
import com.example.data.domain.model.Genre
import com.example.movielusive.Utils
import com.example.movielusive.databinding.ItemMovieGridBinding
import com.example.movielusive.databinding.ItemPillBinding
import com.example.movielusive.main.MovieAdapter
import com.google.android.material.elevation.SurfaceColors

class GenreAdapter(private val listener: GenreAdapterListener) :
    ListAdapter<Genre, GenreAdapter.GenreViewHolder>(GenreDiffCallback) {

    interface GenreAdapterListener {
        fun onGenreClicked(genre: Genre)
    }

    inner class GenreViewHolder(val binding: ItemPillBinding, listener: GenreAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding.tvTitle) {
                setOnClickListener { listener.onGenreClicked(getItem(bindingAdapterPosition)) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        return GenreViewHolder(
            ItemPillBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        with(holder.binding) {
            tvTitle.text = getItem(position).name
        }
    }
}