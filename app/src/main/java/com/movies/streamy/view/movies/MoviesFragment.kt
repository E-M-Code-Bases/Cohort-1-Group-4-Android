package com.movies.streamy.view.movies

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
import com.movies.streamy.databinding.FragmentMoviesBinding
import com.movies.streamy.model.dataSource.network.data.response.MovieId
import com.movies.streamy.utils.Prefs
import com.movies.streamy.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment() {
    // Data binding
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var prefs: Prefs

    private val movieAdapter =
        MovieIdAdapter { data: MovieId -> itemClicked(data) }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        binding.viewModel = viewModel
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
        binding.rvMovies.apply {
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            setHasFixedSize(false)
            (layoutManager as LinearLayoutManager).reverseLayout = true
            (layoutManager as LinearLayoutManager).stackFromEnd = true
            adapter?.setHasStableIds(true)
            adapter = movieAdapter
        }
    }

    private fun setUpRecyclerView(movieIdList: List<MovieId?>?) {
        if (movieIdList?.isEmpty() == true) {
            // binding.noDataGroup.visibility = View.VISIBLE
        } else {
            hideShimmerEffect()
            movieAdapter.submitList(movieIdList)
        }
    }

    private fun onViewStateChanged(state: MoviesViewState) {
        hideShimmerEffect()
        when (state) {
            is MoviesViewState.Loading -> {
                showShimmerEffect()
                // binding.noTextOrder.visibility = View.GONE
                // binding.noImageOrder.visibility = View.GONE
            }

            is MoviesViewState.Success -> {
                hideShimmerEffect()
                // binding.noTextOrder.visibility = View.GONE
                // binding.noImageOrder.visibility = View.GONE
            }

            is MoviesViewState.Error -> {
                hideShimmerEffect()
                // showSnackBar(state.errorMessage, false)
                // binding.noTextOrder.visibility = View.VISIBLE
                // binding.noImageOrder.visibility = View.VISIBLE
            }

            else -> {}
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.rvMovies.visibility = View.VISIBLE
    }

    private fun itemClicked(data: MovieId) {
        //todo
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
