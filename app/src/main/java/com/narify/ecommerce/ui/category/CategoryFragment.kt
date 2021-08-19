package com.narify.ecommerce.ui.category

import android.os.Bundle
import android.view.View
import com.narify.ecommerce.databinding.FragmentCategoryBinding
import com.narify.ecommerce.model.Category
import com.narify.ecommerce.view.BaseFragment

class CategoryFragment :
    BaseFragment<FragmentCategoryBinding>({ FragmentCategoryBinding.inflate(it) }) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvCategory.adapter = CategoryAdapter(
            listOf(
                Category("123", "Clothes"),
                Category("123", "Clothes"),
                Category("123", "Clothes"),
                Category("123", "Clothes"),
                Category("123", "Clothes"),
                Category("123", "Clothes"),
                Category("123", "Clothes"),
                Category("123", "Clothes")
            )
        )

    }

}