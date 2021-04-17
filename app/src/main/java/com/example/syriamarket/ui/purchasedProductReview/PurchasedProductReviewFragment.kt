package com.example.syriamarket.ui.purchasedProductReview

import android.content.res.ColorStateList
import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.syriamarket.R
import com.example.syriamarket.databinding.ProfileFragmentBinding
import com.example.syriamarket.databinding.PurchasedProductReviewFragmentBinding
import com.example.syriamarket.ui.profile.ProfileViewModel

class PurchasedProductReviewFragment : Fragment() {

    companion object {
        fun newInstance() = PurchasedProductReviewFragment()
    }

    private lateinit var viewModel: PurchasedProductReviewViewModel
    private var _binding: PurchasedProductReviewFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PurchasedProductReviewFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PurchasedProductReviewViewModel::class.java)
        binding.reviewViewModel = viewModel
        binding.lifecycleOwner = this
        val productID = requireArguments().getString("productID","N/A")
        val orderDate = requireArguments().getString("createdAt","N/A")
        val orderIsDelivered = requireArguments().getBoolean("isDelivered")
        val orderPrice = requireArguments().getString("price","N/A")
        val orderId = requireArguments().getString("orderId","N/A")
        binding.tvProductPrice.text = orderPrice
        binding.tvProduct.text = productID
        viewModel.details(orderId)
        if (orderIsDelivered){
            binding.tvProductStatus.text = "تم الإستلام"
            binding.tvProductStatus.setTextColor(ColorStateList.valueOf(Color.GREEN))
        }else{
            binding.tvProductStatus.text = "قيد المراجعة"
            binding.tvProductStatus.setTextColor(ColorStateList.valueOf(Color.RED))
        }
        binding.tvProductNumber.text = orderId
        binding.btnRequestRefund.setOnClickListener {
            if (orderIsDelivered){
                AlertDialog.Builder(requireContext())
                        .setTitle("تأكيد طلب مراجعة منتج")
                        .setMessage("هل تريد مراجعة طلب هذا المنتج؟") // Specifying a listener allows you to take an action before dismissing the dialog.
                        // The dialog is automatically dismissed when a dialog button is clicked.
                        .setPositiveButton("تأكيد") { _, _ ->
                            Toast.makeText(context,"جاري مراجعى الطلب",Toast.LENGTH_LONG).show()
                            viewModel.reviewRequest(requireContext(),productID)
                        }
                        .setNegativeButton("إلغاء", null)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show()
            }
            else
                Toast.makeText(requireContext(),"الطلب قيد المراجعة.",Toast.LENGTH_LONG).show()
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PurchasedProductReviewViewModel::class.java)
        // TODO: Use the ViewModel
    }

}