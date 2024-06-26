package com.movies.streamy.view.movies

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentMoviesBinding
import com.movies.streamy.model.dataSource.network.data.response.PopularMovieResult
import com.movies.streamy.utils.Prefs
import com.movies.streamy.utils.observe
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

private const val TAG = "movies"

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private lateinit var binding: FragmentMoviesBinding
    private lateinit var viewModel: MoviesViewModel
    private lateinit var prefs: Prefs

    private val popularMovieAdapter =
        PopularMovieAdapter()

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_movies, container, false)
        viewModel = ViewModelProvider(this)[MoviesViewModel::class.java]

        //binding.viewModel = viewModel
        //binding.lifecycleOwner = viewLifecycleOwner

        viewModel.popularMovies.observe(viewLifecycleOwner, Observer{
            Log.d(TAG, it.toString())
            popularMovieAdapter.asyncList.submitList(it)
            binding.rvMovies.apply {
                layoutManager = GridLayoutManager(requireContext(), 3)
                setHasFixedSize(false)
                //adapter?.setHasStableIds(true)
                adapter = popularMovieAdapter
            }

        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        showShimmerEffect()
       // viewModel.getPopularMovies()
        setUpObservers()
        setUpAdapter()
    }

    private fun setUpObservers() {
      //  observe(viewModel.popularMovies, ::setUpRecyclerView)
        observe(viewModel.viewState, ::onViewStateChanged)
    }

    private fun setUpAdapter() {
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(requireContext(), 3)
            setHasFixedSize(false)
            adapter?.setHasStableIds(true)
            adapter = popularMovieAdapter
        }
    }

//    private fun setUpRecyclerView(movieList: List<PopularMovieResult?>?) {
//        if (movieList?.isEmpty() == true) {
//            // binding.noDataGroup.visibility = View.VISIBLE
//        } else {
//            hideShimmerEffect()
//            popularMovieAdapter.asyncList.submitList(movieList)
//        }
//    }

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

    private fun itemClicked(data: PopularMovieResult) {
        // Handle item click
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
