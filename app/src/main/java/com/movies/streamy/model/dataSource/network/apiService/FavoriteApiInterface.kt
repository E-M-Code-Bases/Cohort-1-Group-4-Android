package com.movies.streamy.model.dataSource.network.apiService

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.favorite.FavoriteMovieResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FavoriteApiInterface {

    @GET("3/account/21322323/favorite/movies")
    suspend fun getFavMovie(
        @Query("language") language: String?,
        @Query("page") page: Int?,
        @Query("sort_by") sort_by: String?,
        @Query("api_key") apiKey: String?

    ): NetworkResponse<FavoriteMovieResponse, ErrorResponse>
}
