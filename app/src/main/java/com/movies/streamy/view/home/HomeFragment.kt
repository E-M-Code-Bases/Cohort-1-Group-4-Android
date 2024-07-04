package com.movies.streamy.view.home

import HomeAdapter
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentHomeBinding
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
import com.movies.streamy.model.dataSource.network.data.response.homeData.TrailerResult
import com.movies.streamy.room.favorites.FavMovieDB
import com.movies.streamy.room.favorites.FavMovieDBRepository
import com.movies.streamy.room.favorites.FavMovieDBViewModel
import com.movies.streamy.room.favorites.FavMovieEntity
import com.movies.streamy.room.favorites.FavoriteViewModelFactory
import com.movies.streamy.utils.Prefs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment : Fragment(), HomeAdapter.OnItemClickListener {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var prefs: Prefs
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var favViewModel: FavMovieDBViewModel

    private var selectedItem: HomeResult? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        prefs = Prefs(requireContext())
        // Initialize FavMovieDBViewModel
        val database = FavMovieDB.getDatabase(requireContext())
        val repository = FavMovieDBRepository(database.FavMovieDao())
        val factory = FavoriteViewModelFactory(repository)
        favViewModel = ViewModelProvider(this, factory).get(FavMovieDBViewModel::class.java)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel

        binding.Trailler.setOnClickListener {
            selectedItem?.let { item ->
                item.id?.let { movieId ->
                    viewModel.setTrailerVisible(true)
                    if (item.media_type == "movie") {
                        viewModel.getTrailerByMovieId(movieId)
                    } else if (item.media_type == "tv") {
                        viewModel.getTrailerBySeriesId(movieId)
                    }
                }
            }
        }
        binding.frameOne.visibility = View.GONE

        viewModel.trailerList.observe(viewLifecycleOwner, Observer { trailerList ->
            if (viewModel.trailerVisible.value == true) {
                trailerList.firstOrNull()?.let { trailer ->
                        playTrailer(trailer)
                } ?: run {
                    Toast.makeText(requireContext(), "Trailer not found", Toast.LENGTH_SHORT).show()
                }
                viewModel.setTrailerVisible(false)
            }
        })
        binding.lifecycleOwner = viewLifecycleOwner
        homeAdapter = HomeAdapter(this)

        binding.FavIcon.setOnClickListener {
            selectedItem?.let { item ->
                toggleFavorite(item)
            }
        }

        binding.detailIcon.setOnClickListener {
            selectedItem?.let { item ->
                showDetails(item)
            }
        }

        viewModel.movieList.observe(viewLifecycleOwner, Observer { movieList ->
            if (movieList.isNullOrEmpty()) {
                // Handle empty case if needed
            } else {
                homeAdapter.asyncList.submitList(movieList)
                binding.AllShows.apply {
                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                    setHasFixedSize(true)
                    adapter = homeAdapter
                }
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
        viewModel.getMovieLists()
        setUpObservers()
    }

    private fun setUpObservers() {
        viewModel.movieList.observe(viewLifecycleOwner, ::setUpRecyclerView)
        viewModel.viewState.observe(viewLifecycleOwner, ::onViewStateChanged)
    }

    private fun setUpRecyclerView(movieList: List<HomeResult?>?) {
        if (movieList.isNullOrEmpty()) {
            // Handle empty case
        } else {
            homeAdapter.asyncList.submitList(movieList)
            binding.AllShows.apply {
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                setHasFixedSize(true)
                adapter = homeAdapter
                addOnScrollListener(object : RecyclerView.OnScrollListener() {
                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                        super.onScrolled(recyclerView, dx, dy)
                        applyScaleTransformation(recyclerView)
                    }
                })
            }
            binding.AllShows.post { applyScaleTransformation(binding.AllShows) }
        }
    }

    private fun onViewStateChanged(state: HomeViewState) {
        hideShimmerEffect()
        when (state) {
            is HomeViewState.Loading -> {
                showShimmerEffect()
            }
            is HomeViewState.Success -> {
                hideShimmerEffect()
            }
            is HomeViewState.Error -> {
                hideShimmerEffect()
                // Handle error state
            }
        }
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.AllShows.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.AllShows.visibility = View.VISIBLE
    }

    override fun onItemClick(item: HomeResult) {
        selectedItem = item
        Toast.makeText(context, "Selected: ${item.name ?: item.title}", Toast.LENGTH_SHORT).show()

        item.id?.let {
            favViewModel.isFavorite(it).observe(viewLifecycleOwner, Observer { isFavorite ->
                updateFavoriteIcon(isFavorite)
            })
        }
    }

    private fun playTrailer(trailer: TrailerResult) {
        val trailerUrl = "https://www.youtube.com/watch?v=${trailer.key}"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl))
        intent.putExtra("force_fullscreen", true)
        startActivity(intent)

        viewModel.setTrailerVisible(false)  // Reset trailer visibility after playing
    }

    private fun addToFavorite(item: HomeResult) {
        val favoriteEntity = FavMovieEntity(
            id = item.id,
            title = item.title,
            media_type = item.media_type,
            poster_path = item.poster_path,
            first_air_date = item.first_air_date,
            adult = item.adult,
            original_language = item.original_language,
            name = item.name,
            original_title = item.original_title,
            original_name = item.original_name,
            vote_count = item.vote_count,
            vote_average = item.vote_average,
            overview = item.overview,
            release_date = item.release_date,
            popularity = item.popularity
        )
        favViewModel.insert(favoriteEntity)
    }

    private fun removeFromFavorite(item: HomeResult) {
        favViewModel.deleteFavoriteById(item.id!!)
    }

    private fun toggleFavorite(item: HomeResult) {
        item.id?.let {
            favViewModel.isFavorite(it).observe(viewLifecycleOwner, Observer { isFavorite ->
                if (isFavorite) {
                    removeFromFavorite(item)
                    updateFavoriteIcon(false)
                } else {
                    addToFavorite(item)
                    updateFavoriteIcon(true)
                }
            })
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        val color = if (isFavorite) R.color.colorAccent else R.color.white
        binding.FavIcon.setColorFilter(ContextCompat.getColor(requireContext(), color))
    }

    private fun showDetails(item: HomeResult) {
    Toast.makeText(context, "Show details", Toast.LENGTH_SHORT).show()
        val fragment = HomeTrendingDetails.newInstance(item)
        binding.frameOne.visibility = View.VISIBLE
        binding.Homepage.visibility = View.GONE
        val tras = childFragmentManager.beginTransaction().replace(binding.frameOne.id, fragment)
        tras.commit()
    }


    private fun applyScaleTransformation(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val midpoint = recyclerView.width / 2f
        val d0 = 0f
        val d1 = 1.2f * midpoint
        val s0 = 1.2f
        val s1 = 0.5f

        for (i in 0 until recyclerView.childCount) {
            val child = recyclerView.getChildAt(i)
            val childMidpoint = (layoutManager.getDecoratedLeft(child) + layoutManager.getDecoratedRight(child)) / 2f
            val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
            val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
            child.scaleX = scale
            child.scaleY = scale
        }
    }
}

