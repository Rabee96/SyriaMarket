package ui.whatsappProducts

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.databinding.WhatsappProductsFragmentBinding

class WhatsappProductsFragment : Fragment() {

    companion object {
        fun newInstance() = WhatsappProductsFragment()
    }

    private lateinit var viewModel: WhatsappProductsViewModel
    private var _binding: WhatsappProductsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = WhatsappProductsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(WhatsappProductsViewModel::class.java)
        viewModel.responseCountry
        binding.whatsAppViewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

}