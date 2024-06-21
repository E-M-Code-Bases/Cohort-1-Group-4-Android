package com.movies.streamy.model.repository.implementation


import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IMoviesDataSource
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.repository.abstraction.IMoviesRepository
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val homeDataSource: IMoviesDataSource,
) : IMoviesRepository {
    override suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse> {
        return homeDataSource.getMovieIds()
    }
}