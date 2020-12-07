package com.example.popcorn.movieDetails

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter

class MovieDetailsAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> DescriptionFragment()
            1 -> GalleryFragment()
            2 -> ActorsFragment()
            else -> throw IllegalStateException()
        }
    }
}