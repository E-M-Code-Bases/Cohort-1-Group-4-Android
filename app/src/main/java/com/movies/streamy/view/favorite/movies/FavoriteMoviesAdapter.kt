package com.movies.streamy.view.favorite.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.movies.streamy.databinding.MovieListVerticalBinding
import com.movies.streamy.room.favorites.FavMovieEntity

class FavoriteMoviesAdapter : ListAdapter<FavMovieEntity, FavoriteMoviesAdapter.MovieViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieListVerticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class MovieViewHolder(private val binding: MovieListVerticalBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: FavMovieEntity) {
            binding.MovieName.text = data.title
            binding.Type.text = data.media_type
            binding.year.text = data.first_air_date
            binding.country.text = data.origin_country
            binding.genre.text = data.title.toString()
            val posterUrl = "https://image.tmdb.org/t/p/w500${data.poster_path}"
            Glide.with(binding.image.context)
                .load(posterUrl)
                .into(binding.image)



//            binding.favMovieEntity = data

            binding.executePendingBindings()
        }
    }

    class MovieDiffCallback : DiffUtil.ItemCallback<FavMovieEntity>() {
        override fun areItemsTheSame(oldItem: FavMovieEntity, newItem: FavMovieEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FavMovieEntity, newItem: FavMovieEntity): Boolean {
            return oldItem == newItem
        }
    }
}
