package com.movies.streamy.view.favorite.series

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.movies.streamy.R

class FavoriteSeriesFragment : Fragment() {

    companion object {
        fun newInstance() = FavoriteSeriesFragment()
    }

    private val viewModel: FavoriteSeriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite_series, container, false)
    }
}