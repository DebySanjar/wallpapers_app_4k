package com.example.wallpapers.adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.firstinubuntu.data.my_model
import com.example.firstinubuntu.databinding.ItemRacyBinding

@Suppress("DEPRECATION")
class MyRecyAdapter(
    private val items: List<my_model>,
    private val onItemClick: (Int) -> Unit
) :
    RecyclerView.Adapter<MyRecyAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemRacyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: my_model, position: Int) {

            Glide.with(binding.itemRecy.context).load(item.img).into(binding.itemRecy)

            binding.root.setOnClickListener {
                onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRacyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], position)
    }

    override fun getItemCount(): Int = items.size
}
