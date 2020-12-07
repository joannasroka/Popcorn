package com.example.popcorn.movieDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.navArgs
import com.example.popcorn.R
import com.example.popcorn.databinding.MovieDetailsFragmentBinding
import com.google.android.material.tabs.TabLayoutMediator

class MovieDetailsFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by activityViewModels()

    private var binding: MovieDetailsFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MovieDetailsFragmentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val movieName = arguments?.getString(MOVIE_ARGUMENT_KEY)
            ?: error("No movie name passed")

        viewModel.load(movieName)

        binding?.let {
            it.movieDetailsViewPager.adapter = MovieDetailsAdapter(this)
            TabLayoutMediator(it.movieDetailsTabs, it.movieDetailsViewPager) { tab, position ->
                tab.text = when (position) {
                    0 -> context?.resources?.getString(R.string.description)
                    1 -> context?.resources?.getString(R.string.gallery)
                    2 -> context?.resources?.getString(R.string.cast)
                    else -> throw IllegalStateException()
                }
            }.attach()

            viewModel.movie.observe(viewLifecycleOwner) { movie ->
                if (movie == null) {
                    return@observe
                }
                it.movieDetailsTitle.text = movie.name
                it.movieDetailsYear.text = movie.year.toString()
                it.movieDetailsCategory.text = movie.category.displayName
                it.movieDetailsFavouriteButton.setImageResource(
                    if (movie.favourite) R.drawable.ic_baseline_favorite_24
                    else R.drawable.ic_baseline_favorite_border_24
                )
                it.movieDetailsFavouriteButton.setOnClickListener {
                    viewModel.onFavourite(movie)
                }
                val context = it.root.context
                val thumbnail = context.resources.getIdentifier(
                    movie.thumbnail,
                    "drawable",
                    context.packageName
                )
                it.moviePosterImageView.setImageResource(thumbnail)
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val MOVIE_ARGUMENT_KEY = "MOVIE"
    }

}