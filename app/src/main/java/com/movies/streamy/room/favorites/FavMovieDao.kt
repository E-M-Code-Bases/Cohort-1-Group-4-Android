package com.movies.streamy.room.favorites

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favoriteItem: FavMovieEntity)

    @Query("SELECT * FROM favorites")
    suspend fun getAllMovieFavorites(): List<FavMovieEntity>

//    @Query("SELECT * FROM favorites WHERE id = :id")
//    suspend fun getFavoriteMovieById(id: Int): FavMovieEntity?
}
//