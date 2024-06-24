package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeTrendingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeApiInterface {
    @GET("3/trending/all/day")
    suspend fun getMovieList(
        @Query("page") page: Int?,
        @Query("language") language: String?,
        @Query("api_key") apiKey: String?
    ): NetworkResponse<HomeTrendingResponse, ErrorResponse>
}
