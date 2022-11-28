package com.example.movielusive.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.data.domain.model.Cast
import com.example.data.domain.model.Filter
import com.example.data.domain.model.Genre
import com.example.movielusive.R
import com.example.movielusive.VMFactory

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, VMFactory())[MainVM::class.java].apply {
            getMovies(getFilter())
        }

        setContentView(R.layout.activity_main)
    }

    private fun getFilter(): Filter? {
        return if (intent.extras != null) {
            Filter(
                intent.getIntExtra("page", 1),
                intent.getStringExtra("query"),
                Genre(intent.getIntExtra("genreId", -1), intent.getStringExtra("nameId")),
                intent.getStringExtra("sortBy"),
                Cast(intent.getIntExtra("castId", 1), intent.getStringExtra("castName"))
            )
        } else null
    }
}