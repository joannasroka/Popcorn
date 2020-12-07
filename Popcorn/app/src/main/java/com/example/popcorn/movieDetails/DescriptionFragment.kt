package com.example.popcorn.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import com.example.popcorn.R
import com.example.popcorn.databinding.DescriptionFragmentBinding
import com.example.popcorn.databinding.MovieDetailsFragmentBinding


class DescriptionFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by activityViewModels()

    private var binding: DescriptionFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DescriptionFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.movie.observe(viewLifecycleOwner) { movie ->
            if (movie == null) {
                return@observe
            }
            binding?.let {
                it.movieDetailsDescription.text = movie.description
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}