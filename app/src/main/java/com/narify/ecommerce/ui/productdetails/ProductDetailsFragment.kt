package com.narify.ecommerce.ui.productdetails

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.databinding.FragmentProductDetailsBinding
import com.narify.ecommerce.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailsFragment :
    BaseFragment<FragmentProductDetailsBinding>({ FragmentProductDetailsBinding.inflate(it) }) {

    private val viewModel: ProductDetailsViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()

    @Inject
    lateinit var pref: AppPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val product = args.clickedProduct!!

            product.let {
                svProductImages.setSliderAdapter(ImageSliderAdapter(it.images!!))
                tvProductTitle.text = it.title
                tvProductDescription.text = it.description
                tvProductPrice.text = it.price?.raw
                rbProductRating.rating = it.rating?.stars!!
                tvProductRatingCount.text = "(${it.rating.count})"
            }

            val cart = pref.getCart()
            btnAddToCart.setOnClickListener {
                cart.addProduct(product)
                pref.saveCart(cart)
                Snackbar.make(it, "Added.", Snackbar.LENGTH_SHORT).show()
            }
        }


    }

}