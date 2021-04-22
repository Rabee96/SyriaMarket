package ui.refund

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.syriamarket.R
import com.example.syriamarket.databinding.ProductsManagerFragmentBinding
import com.example.syriamarket.databinding.RefundFragmentBinding
import ui.products.ProductsManagerViewModel

class RefundFragment(val status: Int) : Fragment() {

    companion object {
        fun newInstance() = RefundFragment(-1)
    }

    private lateinit var viewModel: RefundViewModel
    private var _binding: RefundFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = RefundFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RefundViewModel::class.java)
        binding.refundViewModel = viewModel
        binding.lifecycleOwner = this
        when(status){
            0 -> viewModel.onHoldRequests()
            1 -> viewModel.completedRequests()
            2 -> viewModel.refundRequests()
        }
        return binding.root
    }
}