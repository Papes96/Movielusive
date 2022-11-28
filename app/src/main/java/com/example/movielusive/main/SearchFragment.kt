package com.example.movielusive.main

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.fragment.findNavController
import com.example.data.domain.model.Filter
import com.example.movielusive.R
import com.example.movielusive.VMFactory
import com.example.movielusive.databinding.FragmentSearchBinding
import com.google.android.material.elevation.SurfaceColors
import com.google.android.material.transition.MaterialContainerTransform


class SearchFragment : Fragment(R.layout.fragment_search) {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: MainVM
    private lateinit var im: InputMethodManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)

        with(binding){
            background.setBackgroundColor(SurfaceColors.SURFACE_3.getColor(binding.search.context))
            stroke.setBackgroundColor(SurfaceColors.SURFACE_5.getColor(binding.search.context))
            search.setOnEditorActionListener(OnEnterClick())
            search.requestFocus()
            tiSearch.setStartIconOnClickListener { findNavController().popBackStack() }
        }

        viewModel = ViewModelProvider(activity as ViewModelStoreOwner, VMFactory())[MainVM::class.java]
        activity?.window?.statusBarColor = SurfaceColors.SURFACE_3.getColor(binding.search.context)

        im = context?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        im.showSoftInput(binding.search, InputMethodManager.SHOW_IMPLICIT);

        // By default, the Android Transition system will automatically reverse the enter transition when navigating back, if no return transition is set.
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.navHostFragment
            duration = 500.toLong()
            scrimColor = Color.TRANSPARENT
            startContainerColor = SurfaceColors.SURFACE_3.getColor(binding.search.context)
            endContainerColor = Color.TRANSPARENT
            // Prevents the sudden change of the startContainerColor clear by fading the last 0.09 of the animation
            fadeMode = MaterialContainerTransform.FADE_MODE_OUT
            fadeProgressThresholds = MaterialContainerTransform.ProgressThresholds(0.91F, 1F)
        }

    }

    inner class OnEnterClick: TextView.OnEditorActionListener {
        override fun onEditorAction(p0: TextView?, p1: Int, p2: KeyEvent?): Boolean {
            val filter = Filter(query = p0?.text.toString(), genres = null, sortBy = null, withCast = null)
            viewModel.getMovies(filter)
            im.hideSoftInputFromWindow(view?.windowToken, 0)
            findNavController().popBackStack()
            return true
        }

    }

}