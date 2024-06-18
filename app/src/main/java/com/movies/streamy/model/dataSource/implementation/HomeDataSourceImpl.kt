package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.BuildConfig
import com.movies.streamy.model.dataSource.abstraction.IHomeDataSource
import com.movies.streamy.model.dataSource.network.apiService.HomeApiInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeApiInterface: HomeApiInterface,
) : IHomeDataSource {

    override suspend fun getMovieIds(): NetworkResponse<MovieIdResponse, ErrorResponse> {
        return homeApiInterface.getMovieId(1, BuildConfig.API_KEY)
    }

}