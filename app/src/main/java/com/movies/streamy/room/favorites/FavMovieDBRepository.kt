package com.movies.streamy.room.favorites



import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavMovieDBRepository(private val favoriteDao: FavMovieDao) {

    suspend fun insert(favoriteItem: FavMovieEntity) {
        withContext(Dispatchers.IO) {
            favoriteDao.insert(favoriteItem)
        }
    }

    suspend fun getAllMovieFavorites(): List<FavMovieEntity> {
        return withContext(Dispatchers.IO) {
            favoriteDao.getAllMovieFavorites()
        }
    }


}

