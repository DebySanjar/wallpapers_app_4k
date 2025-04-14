package com.example.firstinubuntu.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.firstinubuntu.data.my_model
import com.example.firstinubuntu.databinding.ItemRacyBinding

class LikeAdapter(private val list: List<my_model>) :
    RecyclerView.Adapter<LikeAdapter.LikeViewHolder>() {

    inner class LikeViewHolder(private val binding: ItemRacyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: my_model) {
            binding.itemRecy.setImageResource(item.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeViewHolder {
        val binding = ItemRacyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LikeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LikeViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}
