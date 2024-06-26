package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.BuildConfig
import com.movies.streamy.model.dataSource.abstraction.IMoviesDataSource
import com.movies.streamy.model.dataSource.network.apiService.MoviesApiInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import retrofit2.Response
import javax.inject.Inject

class MoviesDataSourceImpl @Inject constructor(
    private val moviesApiInterface: MoviesApiInterface,
) : IMoviesDataSource {

    override suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse> {
        return moviesApiInterface.getMovieId(1, BuildConfig.API_KEY)
    }

    override suspend fun getPopularMovies(): Response<PopularMovieResponse> {
        return moviesApiInterface.getPopularMovies(1, BuildConfig.API_KEY)
    }
//    override suspend fun getPopularMovies(page: Int, apiKey: String): PopularMovieResponse {
//        return moviesApiInterface.getPopularMovies(1, BuildConfig.API_KEY)
//    }
}