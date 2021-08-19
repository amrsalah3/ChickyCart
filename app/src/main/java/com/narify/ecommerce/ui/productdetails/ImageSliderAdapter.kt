package com.narify.ecommerce.ui.productdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.narify.ecommerce.R
import com.narify.ecommerce.databinding.ItemImageBinding
import com.smarteist.autoimageslider.SliderViewAdapter


class ImageSliderAdapter(var data: List<String>) :
    SliderViewAdapter<ImageSliderAdapter.SliderAdapterVH>() {

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        return SliderAdapterVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        )
    }

    override fun getCount(): Int = data.size

    override fun onBindViewHolder(holder: SliderAdapterVH, position: Int): Unit =
        holder.bind(data[position])

    fun swapData(data: List<String>) {
        this.data = data
        notifyDataSetChanged()
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        fun bind(imageUrl: String): Unit = with(itemView) {
            val binding = ItemImageBinding.bind(this)
            Glide.with(context).load(imageUrl).into(binding.ivProductImage)
        }
    }
}
