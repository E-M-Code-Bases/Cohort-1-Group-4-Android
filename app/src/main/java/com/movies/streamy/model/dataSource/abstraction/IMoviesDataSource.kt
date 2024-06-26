package com.movies.streamy.model.dataSource.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import retrofit2.Response

interface IMoviesDataSource {
    suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse>

    suspend fun getPopularMovies(): Response<PopularMovieResponse>
//    suspend fun getPopularMovies(page: Int, apiKey: String): PopularMovieResponse
}