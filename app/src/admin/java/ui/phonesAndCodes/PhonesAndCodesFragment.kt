package ui.phonesAndCodes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.syriamarket.R
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.databinding.PhonesAndcodesFragmentBinding
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.interfaces.OnClickHandlerInterface
import com.example.syriamarket.pojo.CreatePhoneCategory
import com.example.syriamarket.pojo.createPhoneCategoryResponse.CreatePhoneCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhonesAndCodesFragment : Fragment(), View.OnClickListener {

    companion object {
        fun newInstance() = PhonesAndCodesFragment()
    }

    private lateinit var viewModel: PhonesAndCodesViewModel
    private var _binding: PhonesAndcodesFragmentBinding? = null
    private val binding get() = _binding!!
    private val catsPhoneAdminService: ApiInterface =
        signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PhonesAndcodesFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(PhonesAndCodesViewModel::class.java)
        binding.phoneViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.responseCatsPhoneAdmin
        binding.ivBtnCancelCreatePhoneCategory.setOnClickListener(this)
        binding.ivCreatePhoneCategory.setOnClickListener(this)
        binding.tvBtnCreatePhoneCategory.setOnClickListener(this)
        return binding.root
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.iv_btn_cancel_create_phone_category -> {
                binding.clPhoneCatLayout.visibility = ConstraintLayout.VISIBLE
                binding.clCreatePhoneCatLayout.visibility = ConstraintLayout.GONE
            }
            R.id.iv_create_phone_category -> {
                binding.clCreatePhoneCatLayout.visibility = ConstraintLayout.VISIBLE
                binding.clPhoneCatLayout.visibility = ConstraintLayout.GONE
            }
            R.id.tv_btn_create_phone_category -> {
                createPhoneCategory()
            }
            R.id.iv_btn_edit_phone_cats -> {
            }
        }
    }

    private fun createPhoneCategory() {
        //TODO الدولة إنجليزي و الإسم بالعربي و الرمز إختياري
        val validation = binding.etCategoryPhoneeIcon.text.toString().isNotEmpty() && binding.etCategoryPhoneeName.text.toString().isNotEmpty()
        if (validation){
            catsPhoneAdminService.createPhoneCategory(CreatePhoneCategory(binding.etServiceCountry.text.toString(),binding.etCategoryPhoneeIcon.text.toString(),binding.etCategoryPhoneeName.text.toString(),0.0,binding.etServiceTag.text.toString())).enqueue(object :
                Callback<CreatePhoneCategoryResponse> {
                override fun onResponse(
                    call: Call<CreatePhoneCategoryResponse>,
                    response: Response<CreatePhoneCategoryResponse>
                ) {
                    when (response.code()) {
                        201 ->{
                            if (response.body() != null) {
                                findNavController().navigate(R.id.nav_phonesAndCodesFragment)
                                binding.clPhoneCatLayout.visibility = ConstraintLayout.VISIBLE
                                binding.clCreatePhoneCatLayout.visibility = ConstraintLayout.GONE
                            }
                        }
                       500 -> Toast.makeText(requireContext(),"الرجاء إختيار إختصار خدمة جديد",Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CreatePhoneCategoryResponse>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                }

            })
        }else
            Toast.makeText(requireContext(),"الرجاء تعبئة الخانات المطلوبة.",Toast.LENGTH_LONG).show()
    }


}