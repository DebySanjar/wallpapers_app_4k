package com.example.firstinubuntu.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.firstinubuntu.R
import com.example.firstinubuntu.data.allList
import com.example.firstinubuntu.databinding.FragmentAllBinding
import com.example.wallpapers.adapters.MyRecyAdapter


class AllFragment : Fragment() {

    lateinit var binding: FragmentAllBinding
    lateinit var myRecyAdapter: MyRecyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAllBinding.inflate(layoutInflater)


        myRecyAdapter = MyRecyAdapter(allList) { selectIndex ->
            val bundle = Bundle().apply {
                putInt("image_url", selectIndex)
                putInt("keylist", 1)
            }


            findNavController().navigate(R.id.showFragment, bundle)
        }
        binding.recy.adapter = myRecyAdapter


        return binding.root
    }

}