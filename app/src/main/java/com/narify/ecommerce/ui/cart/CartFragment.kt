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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewmodel = viewModel

        initAdapter()
    }

    private fun initAdapter() {
        viewModel.cartItems.observe(viewLifecycleOwner) { items ->
            binding.rvCart.adapter = CartAdapter(items).apply {
                addItem = {
                    viewModel.addProduct(it)
                    swapData(items)
                }
                removeItem = {
                    viewModel.removeProduct(it)
                    swapData(items)
                }
            }
        }
    }

}