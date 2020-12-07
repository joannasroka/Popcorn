package com.example.popcorn.movieList

import android.util.Log
import androidx.lifecycle.*
import com.example.popcorn.movie.Movie
import com.example.popcorn.movie.MovieCategory
import com.example.popcorn.movie.MovieRepository

class MovieListViewModel : ViewModel() {
    val movies = MediatorLiveData<List<Movie>>()

    private val appliedCategoryFilters: MutableLiveData<List<MovieCategory>> =
        MutableLiveData(listOf())

    val categoryFilters: LiveData<List<CategoryFilter>> =
        Transformations.map(appliedCategoryFilters) { appliedCategoryFilters ->
            MovieCategory.values()
                .map { category -> CategoryFilter(category in appliedCategoryFilters, category) }
        }

    val favouriteFilter: MutableLiveData<Boolean?> = MutableLiveData(null)

    init {
        movies.addSource(MovieRepository.movies) {
            val favFilterValue = favouriteFilter.value
            val catFilterValue = appliedCategoryFilters.value
            movies.value = filter(it, catFilterValue, favFilterValue)
        }
        movies.addSource(appliedCategoryFilters) {
            val moviesValue = MovieRepository.movies.value
            val favFilterValue = favouriteFilter.value
            movies.value = filter(moviesValue, it, favFilterValue)
        }
        movies.addSource(favouriteFilter) {
            val moviesValue = MovieRepository.movies.value
            val catFilterValue = appliedCategoryFilters.value
            movies.value = filter(moviesValue, catFilterValue, it)
        }

    }

    private fun filter(
        movies: List<Movie>?,
        categoryFilters: List<MovieCategory>?,
        favouriteFilter: Boolean?
    ): List<Movie>? {
        Log.i(MovieListViewModel::class.simpleName, "filter")
        val result = movies ?: return listOf()
        if (favouriteFilter == null && categoryFilters.isNullOrEmpty()) {
            return result
        }
        val firstFilter =
            { movie: Movie -> if (favouriteFilter != null) movie.favourite == favouriteFilter else true }
        val secondFilter =
            { movie: Movie -> if (!categoryFilters.isNullOrEmpty()) movie.category in categoryFilters else true }

        return result.filter {
            firstFilter(it) && secondFilter(it)
        }
    }


    fun onFavourite(movie: Movie) {
        val changedMovie = movie.copy(favourite = !movie.favourite)
        MovieRepository.update(changedMovie)
    }

    fun onFilter(category: MovieCategory) {
        val selectedCategoryFilters = (appliedCategoryFilters.value ?: listOf()) + category
        appliedCategoryFilters.value = selectedCategoryFilters
    }

    fun onFilter() {
        favouriteFilter.value = true
    }

    fun onDeleteFilter(category: MovieCategory) {
        appliedCategoryFilters.value =
            (appliedCategoryFilters.value ?: listOf()).filter { cat -> cat != category }
    }

    fun onDeleteFilter() {
        favouriteFilter.value = null
    }

    fun onClearFilters() {
        favouriteFilter.value = null
        appliedCategoryFilters.value = listOf()
    }

    fun onDelete(movie: Movie) {
        MovieRepository.delete(movie)
    }


}