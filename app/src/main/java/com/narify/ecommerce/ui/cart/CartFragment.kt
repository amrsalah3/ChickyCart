package com.narify.ecommerce.ui.cart

import android.os.Bundle
import android.view.View
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.databinding.FragmentCartBinding
import com.narify.ecommerce.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class CartFragment : BaseFragment<FragmentCartBinding>({ FragmentCartBinding.inflate(it) }) {

    @Inject
    lateinit var pref: AppPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Timber.d("GeneralLogKey onViewCreated: ${pref.getCart()}")
        binding.rvCart.adapter = CartAdapter(pref.getCart().getItems())
    }
}