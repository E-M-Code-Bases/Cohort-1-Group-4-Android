package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.MovieIdResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {
    @GET("3/movie/changes")
    suspend fun getMovieId(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String?
    ): NetworkResponse<MovieIdResponse, ErrorResponse>

}