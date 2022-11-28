package com.example.movielusive.movie

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.data.domain.model.Genre
import com.example.data.domain.model.Movie
import com.example.movielusive.Utils.displayText
import com.example.movielusive.Utils.displayView
import com.example.movielusive.Utils.setPicture
import com.example.movielusive.VMFactory
import com.example.movielusive.databinding.ActivityMovieBinding
import com.example.movielusive.main.MainActivity
import kotlinx.coroutines.launch

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    private lateinit var viewModel: MovieVM
    private val adapter = GenreAdapter(AdapterOnClick())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater).apply {
            rvGenres.adapter = adapter
            rvGenres.layoutManager = LinearLayoutManager(this@MovieActivity, LinearLayoutManager.HORIZONTAL, false)
        }

        viewModel = ViewModelProvider(this, VMFactory())[MovieVM::class.java].apply {
            getMovieDetails(intent.getIntExtra("movieId", -1))
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.movieSelected.collect { setMovie(it) }
            }
        }

        setContentView(binding.root)
    }

    private fun setMovie(movie: Movie) {
        with(binding) {
            val picture = if(movie.backdrop != "")  movie.backdrop else movie.poster
            setPicture(ivBackdrop, picture)
            displayText(tvPlaceHolder, movie.title)
            displayText(tvTitle, movie.title)
            displayText(tvRating, movie.voteAverage)
            displayText(tvTagline, movie.tagline)
            displayText(tvOverview, movie.overview)
            displayView(icvCollection, movie.collection){ icvCollection.setCard(movie.collection) }
            adapter.submitList(movie.genres)
        }
    }

    inner class AdapterOnClick : GenreAdapter.GenreAdapterListener {
        override fun onGenreClicked(genre: Genre) {
            startActivity(Intent(this@MovieActivity, MainActivity::class.java).apply {
                putExtra("genreId", genre.id)
                putExtra("genreName", genre.name)
            })
        }
    }
}