package ui.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.syriamarket.base.App.Companion.setToken
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.databinding.ProfileFragmentBinding
import com.example.syriamarket.pojo.Coupon
import ui.auth.AuthActivity

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel
    private var _binding: ProfileFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        _binding = ProfileFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.userSignedIn(token)
        binding.profileViewModel = viewModel
        binding.lifecycleOwner = this
        binding.ivBtnSignOut.setOnClickListener {
            AlertDialog.Builder(requireContext())
                    .setTitle("تسجيل الخروج")
                    .setMessage("هل تريد تسجيل الخروج؟")
                    // The dialog is automatically dismissed when a dialog button is clicked.
                    .setPositiveButton("خروج") { _, _ ->
                        setToken(requireActivity(), "N/A")
                        val i = Intent(requireActivity(), AuthActivity::class.java)
                        requireActivity().startActivity(i)
                        requireActivity().finish()
                    }
                    .setNegativeButton("إلغاء", null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show()
        }
        binding.btnEditBalance.setOnClickListener {
            binding.clMainProfileLayout.visibility = ConstraintLayout.GONE
            binding.clAddCouponLayout.visibility = ConstraintLayout.VISIBLE
        }
        binding.ivBtnCancelCharge.setOnClickListener {
            binding.clMainProfileLayout.visibility = ConstraintLayout.VISIBLE
            binding.clAddCouponLayout.visibility = ConstraintLayout.GONE
        }
        binding.btnAddBalance.setOnClickListener {
            Log.e("Rabee",binding.etCoupon.text.toString())
            viewModel.addBalance(requireContext(),Coupon(binding.etCoupon.text.toString().toInt()))
            viewModel.userSignedIn(token)
            binding.clMainProfileLayout.visibility = ConstraintLayout.VISIBLE
            binding.clAddCouponLayout.visibility = ConstraintLayout.GONE
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

    }

}