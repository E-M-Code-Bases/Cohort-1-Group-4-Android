package com.movies.streamy.di

import com.movies.streamy.model.dataSource.abstraction.IMoviesDataSource
import com.movies.streamy.model.dataSource.implementation.MoviesDataSourceImpl
import com.movies.streamy.model.dataSource.network.apiService.MoviesApiInterface
import com.movies.streamy.model.repository.abstraction.IMoviesRepository
import com.movies.streamy.model.repository.implementation.MoviesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


//@Module
//
//object MoviesModule {
//    @Provides
//    fun provideMoviesApiService(
//        retrofit: Retrofit
//    ): MoviesApiInterface = retrofit.create(MoviesApiInterface::class.java)
//}
@Module
@InstallIn(SingletonComponent::class)
object MoviesModule {

    @Provides
    @Singleton
    fun provideMoviesApiService(
        retrofit: Retrofit
    ): MoviesApiInterface = retrofit.create(MoviesApiInterface::class.java)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
abstract class MovieBindingModule {

    @Binds
    abstract fun bindHomeDataSourceImpl(impl: MoviesDataSourceImpl): IMoviesDataSource

    @Binds
    abstract fun bindHomeRepositoryImpl(impl: MoviesRepositoryImpl): IMoviesRepository
}