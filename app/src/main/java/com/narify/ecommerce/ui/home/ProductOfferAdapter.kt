package com.narify.ecommerce.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.narify.ecommerce.R
import com.narify.ecommerce.databinding.ItemOfferBinding
import com.narify.ecommerce.model.Product

class ProductOfferAdapter(var data: List<Product>) :
    RecyclerView.Adapter<ProductOfferAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_offer, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<Product>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) = with(itemView) {
            val binding = ItemOfferBinding.bind(this)

            with(binding) {
                Glide.with(context).load(product.getThumbnail()).into(ivProductImage)
                tvProductPriceOffer.text = product.price?.raw
            }

            setOnClickListener {
                // TODO: open up product details
            }
        }
    }
}