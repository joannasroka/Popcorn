package com.example.popcorn.movieDetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popcorn.R
import com.example.popcorn.databinding.GalleryFragmentBinding


class GalleryFragment : Fragment() {
    private val viewModel by activityViewModels<MovieDetailsViewModel>()

    private var binding: GalleryFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = GalleryFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding?.let {
            val adapter = GalleryAdapter()
            it.galleryRecyclerView.adapter = adapter
            it.galleryRecyclerView.layoutManager =
                LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
            viewModel.movie.observe(viewLifecycleOwner) { movie ->
                if (movie == null) {
                    return@observe
                }
                adapter.submitList(movie.images)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}