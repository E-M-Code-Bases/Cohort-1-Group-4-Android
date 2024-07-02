package com.movies.streamy.room.favorites

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteItem: FavMovieEntity)

    @Query("SELECT * FROM favorites")
    fun getAllMovieFavorites(): LiveData<List<FavMovieEntity>>
}