package com.narify.ecommerce.view.custom

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.narify.ecommerce.R
import com.narify.ecommerce.data.local.AppPreferences
import com.narify.ecommerce.util.AppConstants
import com.narify.ecommerce.util.AppConstants.Companion.REQUEST_CODE_SEARCH_FILTERS
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FiltersDialogFragment : BottomSheetDialogFragment() {

    @Inject
    lateinit var pref: AppPreferences

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.dialog_filters)

        dialog.findViewById<Button>(R.id.tv_sort_alphabetical)?.setOnClickListener {
            onSortOptionClicked(AppConstants.FIREBASE_PRODUCT_TITLE)
        }
        dialog.findViewById<Button>(R.id.tv_sort_price)?.setOnClickListener {
            onSortOptionClicked(AppConstants.FIREBASE_PRODUCT_PRICE)
        }
        dialog.findViewById<Button>(R.id.tv_sort_rating)?.setOnClickListener {
            onSortOptionClicked(AppConstants.FIREBASE_PRODUCT_RATING)
        }

        return dialog
    }

    private fun onSortOptionClicked(sortOption: String) {
        pref.saveProductSortOption(sortOption)

        requireActivity().supportFragmentManager.setFragmentResult(
            REQUEST_CODE_SEARCH_FILTERS, bundleOf()
        )
        dismiss()
    }

    companion object {
        const val TAG = "FiltersDialogFragTag"
    }
}