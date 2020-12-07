package com.example.popcorn.movieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.example.popcorn.movie.Movie
import com.example.popcorn.movie.MovieRepository

class MovieDetailsViewModel : ViewModel() {

    private val _movie: MediatorLiveData<Movie?> = MediatorLiveData()

    val movie: LiveData<Movie?> = _movie

    private var addedSources: MutableList<LiveData<Movie?>> = mutableListOf()


    fun load(movieName: String) {
        _movie.value = null
        for (source in addedSources) {
            _movie.removeSource(source)
        }
        addedSources.clear()
        _movie.addSource(MovieRepository.findByName(movieName)) { movie ->
            _movie.value = movie
        }
    }

    fun onFavourite(movie: Movie) {
        val newMovie = movie.copy(favourite = !movie.favourite)
        MovieRepository.update(newMovie)
    }
}