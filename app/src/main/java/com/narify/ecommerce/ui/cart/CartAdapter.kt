package com.narify.ecommerce.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.narify.ecommerce.R
import com.narify.ecommerce.databinding.ItemCartBinding
import com.narify.ecommerce.model.CartItem

class CartAdapter(var data: List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<CartItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: CartItem) = with(itemView) {
            val binding = ItemCartBinding.bind(this)

            with(binding) {
                Glide.with(context).load(item.product.getThumbnail()).into(ivProductImage)
                tvProductTitle.text = item.product.title
                tvProductCount.text = item.count.toString()

                btnProductAdd.setOnClickListener {

                }
            }

        }
    }
}