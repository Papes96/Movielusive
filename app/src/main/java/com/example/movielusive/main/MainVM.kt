package com.example.movielusive.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Repository
import com.example.data.domain.model.Filter
import com.example.data.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainVM : ViewModel() {

    var requestInProgress = false
    private val _movieList = MutableStateFlow(listOf<Movie>())
    val movieList: StateFlow<List<Movie>> = _movieList
    private var _filter = MutableStateFlow(Filter(1, null, null, null, null))
    val filter: StateFlow<Filter> = _filter

    val resultsState = MutableStateFlow<HomeUIState>(HomeUIState.Loading)

    fun getMovies(filter: Filter?){
        if(filter != null) {
            _filter.value = filter
            _movieList.value = listOf<Movie>()
        }
        if(!requestInProgress) getMovies();
    }

    private fun getMovies() {
        requestInProgress = true
        resultsState.value = HomeUIState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { Repository.getMovies(_filter.value) }
                .onSuccess {
                    if (it.isNullOrEmpty()) HomeUIState.NoResults(it)
                    else {
                        _movieList.value = _movieList.value + (it)
                        resultsState.value = HomeUIState.Success(it)
                    }
                    _filter.value.page = _filter.value.page + 1
                    requestInProgress = false
                }.onFailure {
                    resultsState.value = HomeUIState.Failure(it)
                    requestInProgress = false // TODO DRY
            }
        }
    }

    sealed class HomeUIState {
        data class Success(val results: List<Movie>) : HomeUIState()
        data class NoResults(val results: List<Movie>?) : HomeUIState()
        data class Failure(val exception: Throwable) : HomeUIState()
        object Loading : HomeUIState()
    }
}