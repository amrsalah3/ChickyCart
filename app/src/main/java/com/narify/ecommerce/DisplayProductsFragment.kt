package com.narify.ecommerce

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.viewModels
import com.narify.ecommerce.data.remote.api.RealtimeDbProductService
import com.narify.ecommerce.data.repository.ProductRepository
import com.narify.ecommerce.databinding.FragmentDisplayProductsBinding
import com.narify.ecommerce.ui.home.ProductAdapter
import com.narify.ecommerce.view.BaseFragment
import com.narify.ecommerce.view.custom.FiltersDialogFragment
import timber.log.Timber


class DisplayProductsFragment :
    BaseFragment<FragmentDisplayProductsBinding>({ FragmentDisplayProductsBinding.inflate(it) }) {

    val displayProductsViewModel: DisplayProductsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            searchBar.ibFilter.visibility = View.GONE
            searchBar.etSearch.requestFocus()
            val imm =
                requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.showSoftInput(searchBar.etSearch, InputMethodManager.SHOW_FORCED)

            initSearchFilterListener()
        }

        displayProductsViewModel.selectedSort.observe(viewLifecycleOwner, {
            Timber.d("GeneralLogKey initSearchFilterListener: $it")
        })

    }

    private fun initSearchFilterListener() = with(binding) {
        searchBar.etSearch.setOnEditorActionListener { tv, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                ProductRepository(RealtimeDbProductService()).getProductsByName(tv.text.toString())
                    .observe(viewLifecycleOwner, {
                        rvFilteredProducts.adapter = ProductAdapter(it)
                        searchBar.ibFilter.visibility = View.VISIBLE
                    })
                return@setOnEditorActionListener true
            }
            false
        }

        searchBar.ibFilter.setOnClickListener {
            val dialog = FiltersDialogFragment()
            dialog.show(
                requireActivity().supportFragmentManager,
                FiltersDialogFragment.TAG
            )
        }
    }

}