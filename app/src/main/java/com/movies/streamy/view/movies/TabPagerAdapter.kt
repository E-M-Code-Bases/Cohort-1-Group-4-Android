package com.movies.streamy.view.movies

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NowPlayingMoviesFragment()
            1 -> TopRatedMoviesFragment()
            2 -> PopularMoviesFragment()
            else -> NowPlayingMoviesFragment()
        }
    }
}