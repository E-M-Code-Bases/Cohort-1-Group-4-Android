package com.movies.streamy.view.favorite.series

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.movies.streamy.R

import com.movies.streamy.databinding.FragmentFavoriteSeriesBinding
import com.movies.streamy.model.dataSource.network.data.response.MovieId
import com.movies.streamy.utils.Prefs
import com.movies.streamy.utils.observe

import com.movies.streamy.view.movies.MovieIdAdapter
import com.movies.streamy.view.movies.MoviesViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavoriteSeriesFragment: Fragment() {
    private lateinit var binding: FragmentFavoriteSeriesBinding
    private lateinit var viewModel: FavoriteSeriesViewModel
    private lateinit var prefs: Prefs

    private val movieAdapter = MovieIdAdapter { data: MovieId -> itemClicked(data) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(FavoriteSeriesViewModel::class.java)
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite_series, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        showShimmerEffect()
        viewModel.getMovieIds()
        setUpObservers()
        setUpAdapter()
    }

    private fun setUpObservers() {
        observe(viewModel.movieIds, ::setUpRecyclerView)
        observe(viewModel.viewState, ::onViewStateChanged)
    }

    private fun setUpAdapter() {
        binding.FavSeriesRec.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            adapter = movieAdapter
        }
    }

    private fun setUpRecyclerView(movieIdList: List<MovieId?>?) {
        if (movieIdList.isNullOrEmpty()) {
            // Handle empty state
        } else {
            hideShimmerEffect()
            movieAdapter.submitList(movieIdList)
        }
    }

    private fun onViewStateChanged(state: MoviesViewState) {
        when (state) {
            is MoviesViewState.Loading -> showShimmerEffect()
            is MoviesViewState.Success -> hideShimmerEffect()
            is MoviesViewState.Error -> hideShimmerEffect()
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.FavSeriesRec.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.FavSeriesRec.visibility = View.VISIBLE
    }

    private fun itemClicked(data: MovieId) {
        // TODO: Handle item click
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }
}
