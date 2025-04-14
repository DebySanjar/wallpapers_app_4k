package com.example.firstinubuntu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firstinubuntu.R
import com.example.firstinubuntu.data.allList
import com.example.firstinubuntu.data.popularList
import com.example.firstinubuntu.databinding.FragmentRandomBinding
import com.example.wallpapers.adapters.MyRecyAdapter


class RandomFragment : Fragment() {

    private val binding by lazy { FragmentRandomBinding.inflate(layoutInflater) }

    lateinit var myRecyAdapter: MyRecyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        myRecyAdapter = MyRecyAdapter(popularList) { selectIndex ->
            val bundle = Bundle().apply {
                putInt("image_url", selectIndex)
                putInt("keylist", 3)
            }


            findNavController().navigate(R.id.showFragment, bundle)
        }
        binding.recy.adapter = myRecyAdapter




        return binding.root
    }


}