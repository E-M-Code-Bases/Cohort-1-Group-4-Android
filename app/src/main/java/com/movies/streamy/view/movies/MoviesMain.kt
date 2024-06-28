package com.movies.streamy.view.movies


//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.google.android.material.tabs.TabLayoutMediator
//import com.movies.streamy.R
//import kotlinx.android.synthetic.main.activity_main.*
//
//class MoviesMain : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val adapter = TabPagerAdapter(this)
//        R.viewPager.adapter = adapter
//
//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            when (position) {
//                0 -> tab.text = "now playing"
//                1 -> tab.text = "top rated"
//                2 -> tab.text = "Popular"
////                3 -> tab.setIcon(R.drawable.ic_search)
//            }
//        }.attach()
//    }
//}
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.movies.streamy.databinding.FragmentMoviesBinding


class MoviesMain : Fragment() {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager: ViewPager2 = binding.viewPager
        val tabLayout: TabLayout = binding.tabLayout

        val pagerAdapter = TabPagerAdapter(requireActivity())
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "now playing"
                1 -> tab.text = "top rated"
                2 -> tab.text = "Popular"
            }
        }.attach()

//        viewModel.selectedTab.observe(viewLifecycleOwner, Observer { selectedTab ->
//            when (selectedTab) {
//                "Movie" -> viewPager.setCurrentItem(0, true)
//                "Series" -> viewPager.setCurrentItem(1, true)
//            }
//        })
        // Observe ViewModel data
//        viewModel.favoriteMovies.observe(viewLifecycleOwner) { movies ->
//            // Update UI with favorite movies
//        }
//
//        viewModel.favoriteSeries.observe(viewLifecycleOwner) { series ->
//            // Update UI with favorite series
//        }
    }

//        binding.movieFav.setOnClickListener {
//            viewModel.selectTab("Movie")
//        }
//
//        binding.seriesFav.setOnClickListener {
//            viewModel.selectTab("Series")
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}