package ui.allProducts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.databinding.AllProductsFragmentBinding

class AllProductsFragment : Fragment() {

    companion object {
        fun newInstance() = AllProductsFragment()
    }

    private lateinit var viewModel: AllProductsViewModel
    private var _binding: AllProductsFragmentBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val catID = requireArguments().getString("catID","N/A")
        _binding = AllProductsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(AllProductsViewModel::class.java)
        binding.allProductsViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getProduct(catID)
        return binding.root
    }

}