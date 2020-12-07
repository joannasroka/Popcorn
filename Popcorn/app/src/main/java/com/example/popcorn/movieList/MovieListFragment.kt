package com.example.popcorn.movieList

import android.app.ProgressDialog.show
import android.os.Bundle
import android.view.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.popcorn.R
import com.example.popcorn.databinding.MovieListFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.movie_list_fragment.*

class MovieListFragment : Fragment() {

    private val viewModel: MovieListViewModel by activityViewModels()
    private lateinit var movieListAdapter: MovieListAdapter
    private var binding: MovieListFragmentBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_list_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.filter) {
            binding?.let {
                val filtersFragment = MovieListFiltersFragment()
                filtersFragment.show(requireActivity().supportFragmentManager, "FILTER")
            }
        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieListFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bindMoviesRecycler()
    }

    private fun bindMoviesRecycler() {
        movieListAdapter =
            MovieListAdapter({ viewModel.onFavourite(it) }, { viewModel.onDelete(it) })
        binding?.movieListRecyclerView?.let {
            val swipeToDeleteCallback = SwipeToDeleteCallback(movieListAdapter)
            val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
            itemTouchHelper.attachToRecyclerView(it)
        }
        binding?.movieListRecyclerView?.adapter = movieListAdapter
        binding?.movieListRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        viewModel.movies.observe(viewLifecycleOwner) {
            movieListAdapter.submitMovies(it)
        }
    }


    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

}