package com.movies.streamy.room.favorites

import androidx.room.Entity
import androidx.room.PrimaryKey

//data class FavMovieEntity(
////    @PrimaryKey val id: Int,
////    @SerializedName("media_type")
////    val media_type: String,
////    @SerializedName("name")
////    val name: String
//    @PrimaryKey val id: Int?,
//    val title: String?,
//    val media_type: String?,
//    val poster_path: String?
//        )
@Entity(tableName = "favorites")
data class FavMovieEntity(
    @PrimaryKey val id: Int?,
    val adult: Boolean?,
//    val backdrop_path: String?,
    val first_air_date: String?,
//    val genre_ids: Int,
    val media_type: String?,
//    val name: String?,
    val origin_country: String,
//    val original_language: String?,
//    val original_name: String?,
//    val original_title: String?,
//    val overview: String?,
//    val popularity: Double?,
    val poster_path: String?,
//    val release_date: String?,
    val title: String?,
//    val video: Boolean?,
//    val vote_average: Double?,
//    val vote_count: Int?
)
