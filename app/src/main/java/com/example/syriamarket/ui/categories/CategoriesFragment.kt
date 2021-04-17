package com.example.syriamarket.ui.categories

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.databinding.CategoriesFragmentBinding

class CategoriesFragment : Fragment() {

    companion object {
        fun newInstance() = CategoriesFragment()
    }

    private lateinit var viewModel: CategoriesViewModel
    private var _binding: CategoriesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = CategoriesFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        binding.categories = viewModel
        binding.lifecycleOwner = this
        viewModel.responseCats
        viewModel.balance
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CategoriesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}