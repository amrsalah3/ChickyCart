package com.narify.ecommerce.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.data.remote.api.RealtimeDbProductService
import com.narify.ecommerce.data.repository.ProductRepository
import com.narify.ecommerce.databinding.FragmentHomeBinding
import com.narify.ecommerce.model.User
import com.narify.ecommerce.util.Constants.Companion.REQUEST_CODE_SEARCH_FILTERS
import com.narify.ecommerce.view.BaseFragment
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
            putDummyUserToFirebase() // remove it later
            // Uncomment to enable the feature
            /*FiltersDialogFragment().show(
                requireActivity().supportFragmentManager,
                FiltersDialogFragment.TAG
            )*/
        }
    }

    // A temporary function for developing purposes (remove it later)
    private fun putDummyUserToFirebase() {
        User(
            name = User.Name("Amr", "Salah", "Abdelhady"),
            email = "amrhady3@gmail.com",
            photoUrl = "https://firebasestorage.googleapis.com/v0/b/ecommerce-16feb.appspot.com/o/1610443607773.jpeg?alt=media&token=e9ad7247-5014-4375-b704-caff194607bd",
            age = 22,
            gender = User.Gender.MALE,
            phoneNumber = "011534864",
            address = User.Address("15 Texas St, US"),
            card = User.Card("Amr Salah", "123456789"),
            transaction = User.Transaction("ABCDEF")
        ).apply {
            Firebase.database.reference
                .child("users").child("t57HGtfbWiQFrbQvZ4ighMn2tgo1")
                .setValue(this)
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
        ProductRepository(RealtimeDbProductService()).getProducts(sortOption)
            .observe(viewLifecycleOwner) {
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
