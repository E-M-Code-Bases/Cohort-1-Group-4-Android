package com.movies.streamy.view.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavoriteViewModel : ViewModel() {

    // Example data. Replace this with your actual data model.
    private val _favoriteMovies = MutableLiveData<List<String>>()
    val favoriteMovies: LiveData<List<String>> get() = _favoriteMovies

    private val _favoriteSeries = MutableLiveData<List<String>>()
    val favoriteSeries: LiveData<List<String>> get() = _favoriteSeries

    init {
        // Load initial data or set default values
        loadFavoriteMovies()
        loadFavoriteSeries()
    }

    private fun loadFavoriteMovies() {
        // Replace this with your actual data loading logic
        _favoriteMovies.value = listOf("Movie 1", "Movie 2", "Movie 3")
    }

    private fun loadFavoriteSeries() {
        // Replace this with your actual data loading logic
        _favoriteSeries.value = listOf("Series 1", "Series 2", "Series 3")
    }
}
