package com.example.wallpapers.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.firstinubuntu.fragments.AllFragment
import com.example.firstinubuntu.fragments.LikeFragment
import com.example.firstinubuntu.fragments.NatureFragment
import com.example.firstinubuntu.fragments.RandomFragment

@Suppress("DEPRECATION")
class MyFragmentsAdapter(mr: FragmentManager) : FragmentPagerAdapter(mr) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> AllFragment()
            1 -> NatureFragment()
            2 -> RandomFragment()
            3 -> LikeFragment()
            else -> AllFragment()
        }
    }

}