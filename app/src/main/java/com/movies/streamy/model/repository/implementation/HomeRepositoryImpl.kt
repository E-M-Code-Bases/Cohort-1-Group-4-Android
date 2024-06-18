package com.movies.streamy.model.repository.implementation


import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IHomeDataSource
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import com.movies.streamy.model.repository.abstraction.IHomeRepository
import javax.inject.Inject


class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: IHomeDataSource,
) : IHomeRepository {
    override suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse> {
        return homeDataSource.getMovieIds()
    }
}