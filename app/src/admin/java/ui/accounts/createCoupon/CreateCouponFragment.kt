package ui.accounts.createCoupon

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.syriamarket.R
import com.example.syriamarket.databinding.CreateCouponFragmentBinding
import com.example.syriamarket.pojo.CreateCoupon
import com.example.syriamarket.pojo.couponCreatedResponse.CouponCreatedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateCouponFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = CreateCouponFragment()
    }

    private lateinit var viewModel: CreateCouponViewModel
    private var _binding: CreateCouponFragmentBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreateCouponFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateCouponViewModel::class.java)
        binding.couponManagerModelView = viewModel
        binding.lifecycleOwner = this
        viewModel.responseAllCoupons()
        binding.llBtnCreateCoupon.setOnClickListener(this)
        binding.tvBtnCreateCoupon.setOnClickListener(this)
        binding.ivBtnCloseCreateCode.setOnClickListener(this)
        return binding.root
    }
    override fun onClick(v: View) {
        when(v.id){
            R.id.ll_btn_create_coupon ->{
                binding.clCreateCouponLayout.visibility = ConstraintLayout.VISIBLE
                binding.clCouponsManagerLayout.visibility = ConstraintLayout.GONE
            }
            R.id.tv_btn_create_coupon ->{
                createCoupon()
            }
            R.id.iv_btn_close_create_code ->{
                binding.clCouponsManagerLayout.visibility = ConstraintLayout.VISIBLE
                binding.clCreateCouponLayout.visibility = ConstraintLayout.GONE

            }
        }
    }
    fun createCoupon(){
        if (!binding.etCouponBalance.text.isNullOrEmpty()){
            viewModel.allCouponsService.createCoupon(CreateCoupon(binding.etCouponBalance.text.toString().toInt())).enqueue(object :
                    Callback<CouponCreatedResponse> {
                override fun onResponse(call: Call<CouponCreatedResponse>, response: Response<CouponCreatedResponse>) {
                    Log.e("RabeeAllUsers", response.body().toString() + "code" + response.code())
                    if (response.code() == 201) {
                        if (response.body() != null)
                            viewModel.responseAllCoupons()
                        binding.clCouponsManagerLayout.visibility = ConstraintLayout.VISIBLE
                        binding.clCreateCouponLayout.visibility = ConstraintLayout.GONE
                    }
                }

                override fun onFailure(call: Call<CouponCreatedResponse>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                }
            })
        }else
            Toast.makeText(requireContext(),"الرجاء ملئ الخانات المطلوبة.",Toast.LENGTH_LONG).show()
    }
}