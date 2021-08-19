package com.narify.ecommerce.ui.home

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.narify.ecommerce.R
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.data.repository.ProductRepository
import com.narify.ecommerce.databinding.FragmentHomeBinding
import com.narify.ecommerce.util.AppConstants.Companion.FIREBASE_PRODUCT_TITLE
import com.narify.ecommerce.util.AppConstants.Companion.REQUEST_CODE_SEARCH_FILTERS
import com.narify.ecommerce.view.BaseFragment
import com.narify.ecommerce.view.custom.FiltersDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>({ FragmentHomeBinding.inflate(it) }) {

    private val homeViewModel: HomeViewModel by viewModels()
    private val productAdapter = ProductAdapter(emptyList())
    private val offerAdapter = ProductOfferAdapter(emptyList())

    @Inject
    lateinit var pref: AppPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //pref.clearAll()
        initAdapters()
        initSearchFilterBar()
        initFragmentResultListener()
        displayProducts()
    }

    private fun initAdapters() = with(binding) {
        rvFeaturedProducts.adapter = productAdapter
        rvOffers.adapter = offerAdapter
    }

    private fun initSearchFilterBar() = with(binding) {
        toggleSearchFilterIconColor()
        searchBar.etSearch.isFocusable = false
        searchBar.etSearch.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToFilteredProductsFragment())
        }

        searchBar.ibFilter.setOnClickListener {
            FiltersDialogFragment().show(
                requireActivity().supportFragmentManager,
                FiltersDialogFragment.TAG
            )
        }
    }

    private fun initFragmentResultListener() {
        requireActivity().supportFragmentManager.setFragmentResultListener(
            REQUEST_CODE_SEARCH_FILTERS, viewLifecycleOwner, { requestKey, _ ->
                if (requestKey == REQUEST_CODE_SEARCH_FILTERS) {
                    displayProducts()
                }
            }
        )
    }

    private fun displayProducts() {
        toggleSearchFilterIconColor()
        val sortOption = pref.getProductSortOption()
        ProductRepository().getAllProducts(sortOption).observe(viewLifecycleOwner) {
            Timber.d("GeneralLogKey onViewCreated: Observed!")
            productAdapter.swapData(it)
            offerAdapter.swapData(it)
        }
    }

    private fun toggleSearchFilterIconColor() = with(binding.searchBar.ibFilter) {
        val backgroundColor: Int
        val iconColor: Int
        if (pref.getProductSortOption() == FIREBASE_PRODUCT_TITLE) {
            backgroundColor = R.color.white
            iconColor = R.color.black
        } else {
            backgroundColor = R.color.orange
            iconColor = R.color.white
        }
        backgroundTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), backgroundColor))
        imageTintList =
            ColorStateList.valueOf(ContextCompat.getColor(requireContext(), iconColor))
    }

}
