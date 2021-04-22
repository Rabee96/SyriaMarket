package ui.whatsappCats

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.databinding.WhatsAppCatsFragmentBinding

class WhatsAppCatsFragment : Fragment() {

    companion object {
        fun newInstance() = WhatsAppCatsFragment()
    }

    private lateinit var viewModel: WhatsAppCatsViewModel
    private var _binding: WhatsAppCatsFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = WhatsAppCatsFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(WhatsAppCatsViewModel::class.java)
        binding.whatsAppCatsViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.responseCatsWhatsApp
        viewModel.balanceWhatsApp
        return binding.root
    }
}