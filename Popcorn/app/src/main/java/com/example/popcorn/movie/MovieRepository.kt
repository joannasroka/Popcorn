package com.example.popcorn.movie

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json


object MovieRepository {
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>(listOf())
    val movies: LiveData<List<Movie>> = _movies

    fun findByName(movieName: String): LiveData<Movie> = Transformations.map(_movies) { movies ->
        movies.find { it.name == movieName }
    }

    fun init(context: Context) {
        if (_movies.value.isNullOrEmpty()) {
            val movieText = context.assets.open("movie_list.json").bufferedReader().readText()
            _movies.value = Json.decodeFromString<List<Movie>>(movieText)
        }
    }

    fun update(movie: Movie) {
        val current = _movies.value
        if (current != null) {
            val index = current.indexOfFirst { currMovie -> currMovie.name == movie.name }
            val changedMovies =
                current.mapIndexed { i, currMovie -> if (i != index) currMovie else movie }
            _movies.value = changedMovies
        }
    }

    fun delete(movie: Movie) {
        val current = _movies.value
        if (current != null) {
            val index = current.indexOfFirst { currMovie -> currMovie.name == movie.name }
            val changedMovies =
                current.mapIndexedNotNull { i, currMovie -> if (i != index) currMovie else null }
            _movies.value = changedMovies
        }
    }

}