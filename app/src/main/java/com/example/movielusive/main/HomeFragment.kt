package com.example.movielusive.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import android.widget.Toast.LENGTH_SHORT
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.domain.model.Movie
import com.example.movielusive.R
import com.example.movielusive.VMFactory
import com.example.movielusive.databinding.FragmentHomeBinding
import com.example.movielusive.movie.MovieActivity
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.transition.MaterialFade
import kotlinx.coroutines.launch

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: MainVM
    private val adapter = MovieAdapter(AdapterOnClick())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        with(binding) {
            cardField.background.mutate().setTint(SurfaceColors.SURFACE_3.getColor(cardField.context))
            tiSearch.setStartIconOnClickListener {}
            search.setOnClickListener { onSearchClicked() }
            rvMovies.adapter = adapter
            rvMovies.layoutManager = GridLayoutManager(context, 2)
            rvMovies.addOnScrollListener(AdapterOnScroll())
        }

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner, VMFactory())[MainVM::class.java]
        activity?.window?.statusBarColor = Color.TRANSPARENT

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.resultsState.collect {
                        when (it) {
                           is MainVM.HomeUIState.Success -> onSuccess()
                           is MainVM.HomeUIState.NoResults -> Toast.makeText(binding.rvMovies.context, "No results", LENGTH_SHORT).show()
                           is MainVM.HomeUIState.Failure -> Toast.makeText(binding.rvMovies.context, it.exception.message, LENGTH_LONG).show()
                           is MainVM.HomeUIState.Loading -> Toast.makeText(binding.rvMovies.context, "Loading", LENGTH_SHORT).show()
                        }
                    }
                }
                launch {
                    viewModel.filter.collect {
                        binding.search.setText(it.query)
                    }
                }
            }
        }
    }

    private fun onSuccess() {
        adapter.submitList(viewModel.movieList.value)
    }

    private fun onSearchClicked() {
        val directions = HomeFragmentDirections.actionHomeFragmentToSearchFragment()
        val extras = FragmentNavigatorExtras(
            binding.cardField to "image_big",
            binding.tiSearch to "search_field_squared"
        )

        // Transition to HomeFragment to keep it from just disappearing
        exitTransition = MaterialFade().apply { duration = 300.toLong() }
        reenterTransition = MaterialFade().apply { duration = 300.toLong() }
        findNavController().navigate(directions, extras)
    }

    inner class AdapterOnClick : MovieAdapter.MovieAdapterListener {
        override fun onMovieClicked(movie: Movie) {
            startActivity(Intent(activity, MovieActivity::class.java).apply {
                putExtra("movieId", movie.id)
            })
        }
    }

    inner class AdapterOnScroll : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            val totalItemCount = recyclerView.layoutManager?.itemCount
            if (totalItemCount == (binding.rvMovies.layoutManager as GridLayoutManager).findLastVisibleItemPosition() + 1)
                viewModel.getMovies(null)
        }
    }
}