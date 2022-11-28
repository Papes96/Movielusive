package com.example.movielusive.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.data.domain.model.Movie
import com.example.data.domain.model.MovieDiffCallback
import com.example.movielusive.Utils.setPicture
import com.example.movielusive.databinding.ItemMovieGridBinding

class MovieAdapter(private val listener: MovieAdapterListener) :
    ListAdapter<Movie, MovieAdapter.MovieViewHolder>(MovieDiffCallback) {

    interface MovieAdapterListener {
        fun onMovieClicked(movie: Movie)
        //fun onMovieLongPressed(view: View, movie: Movie)
    }

    // TODO Adapt to use both list mode and grid mode
    inner class MovieViewHolder(val binding: ItemMovieGridBinding, listener: MovieAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.ivPoster.setOnClickListener { listener.onMovieClicked(getItem(bindingAdapterPosition)) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            binding = ItemMovieGridBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), listener
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.tvTitle.text = getItem(position).title
        setPicture(holder.binding.ivPoster, getItem(position).poster)
    }
}