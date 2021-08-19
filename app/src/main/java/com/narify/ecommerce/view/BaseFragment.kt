package com.narify.ecommerce.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding>(private val viewBinder: (LayoutInflater) -> B) :
    Fragment() {

    private var _binding: B? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return viewBinder(inflater).let {
            _binding = it
            it.root
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

}