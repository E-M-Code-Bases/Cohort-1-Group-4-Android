package com.movies.streamy.room.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class FavMovieDBViewModel(private val repository: FavMovieDBRepository) : ViewModel() {

    fun insert(favoriteItem: FavMovieEntity) {
        viewModelScope.launch {
            repository.insert(favoriteItem)
        }
    }

    fun getFavoriteMovies() = repository.getFavoriteMovies()

    fun getFavoriteSeries() = repository.getFavoriteSeries()
}

class FavoriteViewModelFactory(private val repository: FavMovieDBRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return FavMovieDBViewModel(repository) as T
    }
}
