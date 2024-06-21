package com.movies.streamy.model.dataSource.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse

interface IMoviesDataSource {
    suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse>
}