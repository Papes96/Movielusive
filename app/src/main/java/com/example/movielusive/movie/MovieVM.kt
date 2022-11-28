package com.example.movielusive.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MovieVM: ViewModel() {

    private val _movieSelected = MutableStateFlow(Movie())
    val movieSelected: StateFlow<Movie> = _movieSelected

    fun getMovieDetails(MovieId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                Repository.getMovieDetails(MovieId)
            }.onSuccess {
                if (it != null) {
                    _movieSelected.value = it
                }
            }
        }
    }
}