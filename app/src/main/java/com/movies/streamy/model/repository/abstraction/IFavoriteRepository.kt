package com.movies.streamy.model.repository.abstraction

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.favorite.FavoriteMovieResponse

interface IFavoriteRepository {
    suspend fun getFavMovies(): NetworkResponse<FavoriteMovieResponse, ErrorResponse>

}
