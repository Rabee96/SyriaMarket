package ui.phoneCountries

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.R
import com.example.syriamarket.databinding.CategoriesFragmentBinding
import com.example.syriamarket.databinding.PhoneCountriesFragmentBinding
import ui.categories.CategoriesViewModel

class PhoneCountriesFragment : Fragment() {

    companion object {
        fun newInstance() = PhoneCountriesFragment()
    }

    private lateinit var viewModel: PhoneCountriesViewModel
    private var _binding: PhoneCountriesFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhoneCountriesFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PhoneCountriesViewModel::class.java)
        binding.phonesCountryViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getCountries()
        viewModel.balance
        return binding.root
    }
}