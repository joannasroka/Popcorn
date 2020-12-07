package com.example.popcorn.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popcorn.databinding.MovieListFiltersBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MovieListFiltersFragment : BottomSheetDialogFragment() {

    val viewModel: MovieListViewModel by activityViewModels()

    private lateinit var categoryFilterFilterListAdapter: CategoryFilterListAdapter

    var binding: MovieListFiltersBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListFiltersBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindCategoryFiltersRecycler()
        binding?.clearCategoriesFilterButton?.setOnClickListener {
            viewModel.onClearFilters()
        }
        binding?.showFavouritesFilter?.setOnClickListener {
            binding?.showFavouritesFilter?.let {
                if (it.isChecked) {
                    viewModel.onFilter()
                } else {
                    viewModel.onDeleteFilter()
                }
            }
        }
        viewModel.favouriteFilter.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }
            binding?.showFavouritesFilter?.isChecked = it
        }
    }

    private fun bindCategoryFiltersRecycler() {
        categoryFilterFilterListAdapter =
            CategoryFilterListAdapter({ viewModel.onFilter(it) }, { viewModel.onDeleteFilter(it) })
        binding?.categoryFilterRecyclerView?.adapter = categoryFilterFilterListAdapter
        binding?.categoryFilterRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        viewModel.categoryFilters.observe(viewLifecycleOwner) {
            categoryFilterFilterListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}