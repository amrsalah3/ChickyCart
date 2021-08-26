package com.narify.ecommerce.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.narify.ecommerce.R
import com.narify.ecommerce.databinding.ItemCartBinding
import com.narify.ecommerce.model.CartItem
import com.narify.ecommerce.model.Product

class CartAdapter(
    var items: List<CartItem>,
    var addItem: ((product: Product) -> Unit)? = null,
    var removeItem: ((product: Product) -> Unit)? = null
) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position])

    fun swapData(items: List<CartItem>) {
        this.items = items
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCartBinding.bind(itemView)

        fun bind(item: CartItem) = with(itemView) {
            with(binding) {
                Glide.with(context).load(item.product.getThumbnail()).into(ivProductImage)
                tvProductTitle.text = item.product.title
                tvProductCount.text = item.count.toString()

                btnProductAdd.setOnClickListener { addItem?.invoke(item.product) }
                btnProductSubtract.setOnClickListener { removeItem?.invoke(item.product) }
            }

        }
    }

}