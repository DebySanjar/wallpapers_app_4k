package com.example.firstinubuntu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.example.firstinubuntu.R
import com.example.firstinubuntu.data.tabList
import com.example.firstinubuntu.databinding.FragmentHomeBinding
import com.example.firstinubuntu.databinding.ItemTabBinding
import com.example.wallpapers.adapters.MyFragmentsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.tabs.TabLayout


class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    lateinit var myFragmentsAdapter: MyFragmentsAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)


        myFragmentsAdapter = MyFragmentsAdapter(childFragmentManager)
        binding.myPager.adapter = myFragmentsAdapter
        binding.myTab.setupWithViewPager(binding.myPager)

        loadtab()


        return binding.root
    }

    private fun loadtab() {
        val tabCount = binding.myTab.tabCount

        for (i in 0 until tabCount) {
            val tabView = ItemTabBinding.inflate(layoutInflater)
            val tab = binding.myTab.getTabAt(i)
            tab?.customView = tabView.root


            if (i != 0) {
                tabView.circleIndicator.visibility = View.GONE
            }

            tabView.titleTab.text = tabList[i].title
        }
        binding.myTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<MaterialCardView>(R.id.circle_indicator)?.visibility =
                    View.VISIBLE
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val customView = tab?.customView
                customView?.findViewById<MaterialCardView>(R.id.circle_indicator)?.visibility =
                    View.GONE
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })


    }

}