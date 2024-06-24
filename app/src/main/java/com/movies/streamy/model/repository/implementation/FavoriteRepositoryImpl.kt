package com.movies.streamy.model.repository.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.abstraction.IFavoriteDataSource
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.favorite.FavoriteMovieResponse
import com.movies.streamy.model.repository.abstraction.IFavoriteRepository
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    private val favoriteDataSource: IFavoriteDataSource
) : IFavoriteRepository {
    override suspend fun getFavMovies(): NetworkResponse<FavoriteMovieResponse, ErrorResponse> {
        return favoriteDataSource.getFavMovies()
    }
}
