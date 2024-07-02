package com.movies.streamy.room.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavMovieDBRepository(private val favoriteDao: FavMovieDao) {
    suspend fun insert(favoriteItem: FavMovieEntity) {
        withContext(Dispatchers.IO) {
            favoriteDao.insert(favoriteItem)
        }
    }

    fun getFavoriteMovies(): LiveData<List<FavMovieEntity>> {
        return favoriteDao.getAllMovieFavorites().map { list ->
            list.filter { it.media_type == "movie" }
        }
    }

    fun getFavoriteSeries(): LiveData<List<FavMovieEntity>> {
        return favoriteDao.getAllMovieFavorites().map { list ->
            list.filter { it.media_type == "tv" }
        }
    }
}
