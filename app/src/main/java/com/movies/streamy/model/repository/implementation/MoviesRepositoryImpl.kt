package com.movies.streamy.model.repository.implementation


import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IMoviesDataSource
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResponse
import com.movies.streamy.model.repository.abstraction.IMoviesRepository
import retrofit2.Response
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val moviesDataSource: IMoviesDataSource,
) : IMoviesRepository {
    override suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse> {
        return moviesDataSource.getMovieIds()
    }
    override suspend fun getPopularMovies(): Response<PopularMovieResponse> {
    return moviesDataSource.getPopularMovies()
}
}