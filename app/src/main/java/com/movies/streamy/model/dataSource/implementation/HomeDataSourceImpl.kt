package com.movies.streamy.model.dataSource.implementation

import com.haroldadmin.cnradapter.NetworkResponse
import com.movies.streamy.BuildConfig
import com.movies.streamy.di.RetrofitInitializerNoDI
import com.movies.streamy.model.dataSource.abstraction.IHomeDataSource
import com.movies.streamy.model.dataSource.network.apiService.HomeApiInterface
import com.movies.streamy.model.dataSource.network.apiService.TrailerInterface
import com.movies.streamy.model.dataSource.network.data.response.ErrorResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeTrendingResponse
import com.movies.streamy.model.dataSource.network.data.response.homeData.PlayTrailerResponse
import retrofit2.Response
import javax.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeApiInterface: HomeApiInterface
) : IHomeDataSource {

    override suspend fun getMovieLists(): NetworkResponse<HomeTrendingResponse, ErrorResponse> {
        return homeApiInterface.getMovieList(1, "en-US", BuildConfig.API_KEY)
    }


}

class TrailerImpl(private val trailerInterface: TrailerInterface) {
    suspend fun getTrailerByMovieId(movieId: Int): PlayTrailerResponse? {
        return when (val response = trailerInterface.playTrailer(movieId , apiKey = BuildConfig.API_KEY)) {
            is NetworkResponse.Success -> response.body
            else -> null
        }
    }
}

//class TrailerImpl {
//    // Existing methods...
//
//    suspend fun getTrailerByMovieId(movieId: Int): PlayTrailerResponse? {
//        // Implement network call to fetch trailer using movie ID
//        // Replace with actual network call implementation
//        return homeApiInterface.getTrailers(movieId)
//    }
//}
