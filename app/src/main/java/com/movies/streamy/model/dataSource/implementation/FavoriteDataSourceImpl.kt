package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.BuildConfig
import com.movies.streamy.model.dataSource.abstraction.IFavoriteDataSource
import com.movies.streamy.model.dataSource.network.apiService.FavoriteApiInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.favorite.FavoriteMovieResponse
import javax.inject.Inject

class FavoriteDataSourceImpl @Inject constructor(
    private val favoriteApiInterface: FavoriteApiInterface
) : IFavoriteDataSource {

    override suspend fun getFavMovies(): NetworkResponse<FavoriteMovieResponse, ErrorResponse> {
        return favoriteApiInterface.getFavMovie("en-US", 1, "created_at.asc", BuildConfig.API_KEY)
    }
}
