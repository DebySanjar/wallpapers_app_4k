package com.example.firstinubuntu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firstinubuntu.R
import com.example.firstinubuntu.binding
import com.example.firstinubuntu.data.LikeObject
import com.example.firstinubuntu.data.allList
import com.example.firstinubuntu.databinding.FragmentLikeBinding
import com.example.wallpapers.adapters.MyRecyAdapter


class LikeFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private val binding by lazy { FragmentLikeBinding.inflate(layoutInflater) }
    lateinit var myRecyAdapter: MyRecyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {


        myRecyAdapter = MyRecyAdapter(LikeObject.likeList) { selectIndex ->
            val bundle = Bundle().apply {
                putInt("image_url", selectIndex)
                putInt("keylist", 4)
            }


            findNavController().navigate(R.id.showFragment, bundle)
        }
        binding.recy.adapter = myRecyAdapter


        return binding.root
    }


}