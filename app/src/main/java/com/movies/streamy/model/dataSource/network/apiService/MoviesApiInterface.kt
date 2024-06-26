package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MoviesApiInterface {

    //TRENDING

//    https://api.themoviedb.org/3/trending/movie/week?language=en-US&api_key=5224f1dfc22fcd65e22ba747eee0be84
//    Popular per day:https://api.themoviedb.org/3/trending/movie/day?language=en-US&api_key=5224f1dfc22fcd65e22ba747eee0be84
    //LATEST
//    https://api.themoviedb.org/3/movie/latest


    @GET("movie/changes")
    suspend fun getMovieId(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String?
    ): NetworkResponse<MovieIdResponse, ErrorResponse>

    //    POPULAR
//    https://api.themoviedb.org/3/person/popular?language=en-US&page=1'
//    @GET("3/movie/popular")
//    suspend fun getPopularMovie(
//        @Query("page") page: Int,
//        @Query("api_key") apiKey: String?
//    ): NetworkResponse<List<PopularMovieResponse>, ErrorResponse>

//        @GET("movie/popular")
//        suspend fun getPopularMovies(
//            @Query("page") page: Int,
//            @Query("api_key") apiKey: String
//        ): PopularMovieResponse
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String
    ): Response<PopularMovieResponse>
    }
