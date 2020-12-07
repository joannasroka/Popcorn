package com.example.popcorn.movieList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.getSystemService
import androidx.core.view.ViewCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.popcorn.R
import com.example.popcorn.databinding.MovieListItemBinding
import com.example.popcorn.movie.Movie
import com.example.popcorn.movieDetails.MovieDetailsFragment

class MovieListAdapter(
    val onFavourite: (Movie) -> Unit,
    val onDelete: (Movie) -> Unit,
) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {

    private var movies: List<Movie>? = null

    class MovieListViewHolder(val binding: MovieListItemBinding) : RecyclerView.ViewHolder(
        binding.root
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        return MovieListViewHolder(
            MovieListItemBinding.inflate(
                parent.context.getSystemService<LayoutInflater>()!!,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return movies?.size ?: 0
    }

    fun submitMovies(movies: List<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
        val movie = movies?.get(position) ?: return
        holder.binding.root.setOnClickListener {
            val arguments = Bundle()
            arguments.putString(MovieDetailsFragment.MOVIE_ARGUMENT_KEY, movie.name)
            it.findNavController()
                .navigate(R.id.action_movieListFragment_to_movieDetailsFragment, arguments)
        }
        holder.binding.movieTitleText.text = movie.name
        holder.binding.movieCategoryText.text = movie.category.displayName
        holder.binding.starButton.setImageResource(
            if (movie.favourite) R.drawable.ic_baseline_favorite_24 else
                R.drawable.ic_baseline_favorite_border_24
        )
        holder.binding.starButton.setOnClickListener {
            onFavourite(movie)
        }
        holder.binding.movieCardView.strokeColor = movie.category.borderColor

        val context = holder.binding.root.context
        val imageResource =
            context.resources.getIdentifier(movie.thumbnail, "drawable", context.packageName)
        holder.binding.movieImage.setImageResource(imageResource)
    }

    fun deleteItem(position: Int) {
        val item = movies?.get(position) ?: return
        movies = movies?.filterIndexed { index, _ ->
            index != position
        }
        notifyItemRemoved(position)
        onDelete(item)
    }
}