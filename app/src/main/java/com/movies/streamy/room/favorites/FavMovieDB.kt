package com.movies.streamy.room.favorites

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

@Database(entities = [FavMovieEntity::class], version = 1)
abstract class FavMovieDB : RoomDatabase() {
    abstract fun FavMovieDao(): FavMovieDao

    companion object {
        @Volatile private var INSTANCE: FavMovieDB ? = null

        fun getDatabase(context: Context): FavMovieDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavMovieDB ::class.java,
                    "MovieFav"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

