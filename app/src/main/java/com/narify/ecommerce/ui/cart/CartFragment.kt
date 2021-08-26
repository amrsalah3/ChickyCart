package com.narify.ecommerce.ui.cart

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.narify.ecommerce.databinding.FragmentCartBinding
import com.narify.ecommerce.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>({ FragmentCartBinding.inflate(it) }) {

    private val viewModel: CartViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
    }

    private fun initAdapter() {
        binding.rvCart.adapter = CartAdapter(viewModel.getItems()).apply {
            addItem = {
                viewModel.addItem(it)
                swapData(viewModel.getItems())
            }
            removeItem = {
                viewModel.removeItem(it)
                swapData(viewModel.getItems())
            }
        }
    }

}