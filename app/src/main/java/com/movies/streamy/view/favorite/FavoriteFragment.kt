package com.movies.streamy.view.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movies.streamy.R
import com.movies.streamy.databinding.FragmentFavoriteBinding

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment using View Binding
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        // Set up the click listener using View Binding
//              binding.movieFav.setOnClickListener {
//            findNavController().navigate(R.id.navigation_movies_fav) // Navigate to FavoriteMoviesFragment
//        }
//        binding.seriesFav.setOnClickListener {
//            findNavController().navigate(R.id.navigation_series_fav) // Navigate to FavoriteSeriesFragment
//        }


    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    }
