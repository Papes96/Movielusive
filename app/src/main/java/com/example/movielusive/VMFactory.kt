package com.example.movielusive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movielusive.main.MainVM
import com.example.movielusive.movie.MovieVM


//https://medium.com/koderlabs/viewmodel-with-viewmodelprovider-factory-the-creator-of-viewmodel-8fabfec1aa4f
class VMFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST") // TODO Fix suppress
        return when(modelClass) {
            MainVM::class.java -> MainVM() as T
            MovieVM::class.java -> MovieVM() as T
            else -> throw IllegalArgumentException("ViewModel Not Found")}
    }
}