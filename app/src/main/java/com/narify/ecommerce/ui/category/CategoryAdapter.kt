package com.narify.ecommerce.ui.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.narify.ecommerce.R
import com.narify.ecommerce.databinding.ItemCategoryBinding
import com.narify.ecommerce.model.Category

class CategoryAdapter(var data: List<Category>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_category, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<Category>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) = with(itemView) {
            val binding: ItemCategoryBinding = ItemCategoryBinding.bind(this)

            binding.tvCategoryName.text = category.name

            setOnClickListener {
                // TODO: Handle on click
            }
        }
    }
}