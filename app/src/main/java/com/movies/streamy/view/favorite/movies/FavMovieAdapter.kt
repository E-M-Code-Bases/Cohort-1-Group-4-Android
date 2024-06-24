package com.movies.streamy.view.favorite.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.movies.streamy.databinding.MovieListVerticalBinding
import com.movies.streamy.model.dataSource.network.data.response.favorite.FavoriteMovieResponse

class FavMovieAdapter(private val favoriteMovies: List<FavoriteMovieResponse>) :
    RecyclerView.Adapter<FavMovieAdapter.FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = MovieListVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(favoriteMovies[position])
    }

    override fun getItemCount(): Int = favoriteMovies.size

    inner class FavoriteViewHolder(private val binding: MovieListVerticalBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: FavoriteMovieResponse) {
            // Bind data to views
            binding.movie = movie

            // Load image using Glide
//            Glide.with(binding.image.context)
//                .load(movie.posterPath) // Make sure posterPath is the correct field in FavoriteMovieResponse
//                .into(binding.image)
//
//            // Set other text fields if necessary
//            binding.MovieName.text = movie.title
//            binding.Type.text = movie.type
//            binding.year.text = movie.year.toString()
//            binding.country.text = movie.country
//            binding.genre.text = movie.genre
        }
    }
}
