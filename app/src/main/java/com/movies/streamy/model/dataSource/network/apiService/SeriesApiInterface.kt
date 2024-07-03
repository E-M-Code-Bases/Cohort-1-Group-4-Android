package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesLatestResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesPopularResponse
import com.movies.streamy.model.dataSource.network.data.response.TvSeriesTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.TvseriesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SeriesApiInterface {
    @GET("3/genre/tv/list")
    suspend fun getTvSeriesId(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String?
    ): NetworkResponse<TvseriesResponse, ErrorResponse>

     @GET("3/tv/top_rated")
    suspend fun getTrendingSeries(
    @Query("page") page: Int,
    @Query("api_key") apiKey: String?
    ): NetworkResponse<TvSeriesTrendingResponse, ErrorResponse>

    @GET("3/tv/popular")
    suspend fun getPopularSeries(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String?
    ): NetworkResponse<TvSeriesPopularResponse, ErrorResponse>
    @GET("3/tv/airing_today")
    suspend fun getLatestSeries(
        @Query("page") page: Int,
        @Query("api_key") apiKey: String?
    ): NetworkResponse<TvSeriesLatestResponse, ErrorResponse>
}
