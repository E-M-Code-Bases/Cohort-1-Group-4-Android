package com.movies.streamy.view.home

import HomeAdapter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentHomeBinding
import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
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

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        prefs = Prefs(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        homeAdapter = HomeAdapter(this)

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
            // Handle empty case if needed
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
            binding.AllShows.post{applyScaleTransformation(binding.AllShows)}

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
        // Handle item click

    }
}
private fun applyScaleTransformation(recyclerView: RecyclerView){
    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
    val midpoint = recyclerView.width/2f
    val d0 = 0f
    val d1 =0.9f * midpoint
    val s0 = 1f
    val s1 = 0.7f

    for (i in 0 until recyclerView.childCount) {
        val child = recyclerView.getChildAt(i)
        val childMidpoint = (layoutManager.getDecoratedLeft(child) + layoutManager.getDecoratedRight(child)) / 2f
        val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
        val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
        child.scaleX = scale
        child.scaleY = scale
    }
}

//package com.movies.streamy.view.home
//
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.annotation.RequiresApi
//import androidx.databinding.DataBindingUtil
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.Observer
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.LinearLayoutManager
//import androidx.recyclerview.widget.RecyclerView
//import com.movies.streamy.R
//import com.movies.streamy.databinding.FragmentHomeBinding
//import com.movies.streamy.model.dataSource.network.data.response.homeData.HomeResult
//import com.movies.streamy.utils.Prefs
//import dagger.hilt.android.AndroidEntryPoint
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//
//
//@ExperimentalCoroutinesApi
//@AndroidEntryPoint
//class HomeFragment : Fragment() {
//    private lateinit var binding: FragmentHomeBinding
//    private lateinit var viewModel: HomeViewModel
//    private lateinit var prefs: Prefs
//
//    private val homeAdapter: HomeAdapter =  HomeAdapter()
//
//    @RequiresApi(Build.VERSION_CODES.M)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]
//        prefs = Prefs(requireContext())
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
//        binding.viewModel = viewModel
//        binding.lifecycleOwner = viewLifecycleOwner
//
//
//        viewModel.movieList.observe(viewLifecycleOwner, Observer {movieList ->
//            Log.d("HomeFragment", "Fetched Movie List: $movieList")
//            if (movieList.isNullOrEmpty()) {
//                // Handle empty case if needed
//            } else {
////            hideShimmerEffect()
//                homeAdapter.asyncList.submitList(movieList)
//                binding.AllShows.apply {
//                    layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                    setHasFixedSize(true)
//                    adapter = homeAdapter
//                }
//            }
//        })
//
//
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        initViews()
//    }
//
//    private fun initViews() {
//        showShimmerEffect()
//        viewModel.getMovieLists()
//        setUpObservers()
//        setUpAdapter()
//    }
//
//    private fun setUpObservers() {
//        viewModel.movieList.observe(viewLifecycleOwner, ::setUpRecyclerView)
//        viewModel.viewState.observe(viewLifecycleOwner, ::onViewStateChanged)
//    }
//    private fun setUpAdapter() {
//        binding.AllShows.apply {
//            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//            setHasFixedSize(true)
//            adapter = homeAdapter
//        }
//    }
//    private fun setUpRecyclerView(movieList: List<HomeResult?>?) {
//        Log.d("HomeFragment", "Fetched Movie List: $movieList")
//        if (movieList.isNullOrEmpty()) {
//            // Handle empty case if needed
//        } else {
////            hideShimmerEffect()
//            homeAdapter.asyncList.submitList(movieList)
//            binding.AllShows.apply {
//                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
//                setHasFixedSize(true)
//                adapter = homeAdapter
//                addOnScrollListener(object : RecyclerView.OnScrollListener() {
//                    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                        super.onScrolled(recyclerView, dx, dy)
//                        applyScaleTransformation(recyclerView)
//                    }
//                })
//            }
//            binding.AllShows.post{applyScaleTransformation(binding.AllShows)}
//        }
//    }
//    private fun onViewStateChanged(state: HomeViewState) {
//        hideShimmerEffect()
//        when (state) {
//            is HomeViewState.Loading -> {
//                showShimmerEffect()
//            }
//            is HomeViewState.Success -> {
//                hideShimmerEffect()
//            }
//            is HomeViewState.Error -> {
//                hideShimmerEffect()
//                // Handle error state
//            }
//        }
//    }
//
//    private fun showShimmerEffect() {
//        binding.shimmerFrameLayout.startShimmer()
//        binding.shimmerFrameLayout.visibility = View.VISIBLE
//        binding.AllShows.visibility = View.GONE
//    }
//
//    private fun hideShimmerEffect() {
//        binding.shimmerFrameLayout.stopShimmer()
//        binding.shimmerFrameLayout.visibility = View.GONE
//        binding.AllShows.visibility = View.VISIBLE
//    }
//
//    private fun itemClicked(data: HomeResult) {
//        // Handle item click
//    }
//}
//
//private fun applyScaleTransformation(recyclerView: RecyclerView){
//    val layoutManager = recyclerView.layoutManager as LinearLayoutManager
//    val midpoint = recyclerView.width/2f
//    val d0 = 0f
//    val d1 =0.9f * midpoint
//    val s0 = 1f
//    val s1 = 0.7f
//
//    for (i in 0 until recyclerView.childCount) {
//        val child = recyclerView.getChildAt(i)
//        val childMidpoint = (layoutManager.getDecoratedLeft(child) + layoutManager.getDecoratedRight(child)) / 2f
//        val d = Math.min(d1, Math.abs(midpoint - childMidpoint))
//        val scale = s0 + (s1 - s0) * (d - d0) / (d1 - d0)
//        child.scaleX = scale
//        child.scaleY = scale
//    }
//}