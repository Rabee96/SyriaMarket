package ui.phones.country

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import com.example.syriamarket.R
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.databinding.CreateCountryFragmentBinding
import com.example.syriamarket.databinding.ProductsManagerFragmentBinding
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.CreateCountry
import com.example.syriamarket.pojo.CreatePhoneCategory
import com.example.syriamarket.pojo.countryCreateResponse.CountryCreateResponse
import com.example.syriamarket.pojo.createPhoneCategoryResponse.CreatePhoneCategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ui.products.ProductsManagerViewModel

class CreateCountryFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = CreateCountryFragment()
    }

    private lateinit var viewModel: CreateCountryViewModel
    private var _binding: CreateCountryFragmentBinding? = null
    private val binding get() = _binding!!
    private val countryAdminService: ApiInterface =
            signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)
    private var cPhone =""
    override fun onAttach(context: Context) {
        super.onAttach(context)
        cPhone = requireArguments().getString("catPhoneID","N/A")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = CreateCountryFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreateCountryViewModel::class.java)
        binding.countryViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getCountries()
        binding.ivBtnCreateCountry.setOnClickListener(this)
        binding.ivBtnCloseCountryCreation.setOnClickListener(this)
        binding.tvBtnCreateCountry.setOnClickListener(this)
        return binding.root
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.iv_btn_create_country -> {
                binding.clCountryCreationLayout.visibility = ConstraintLayout.VISIBLE
                binding.clCountryListLayout.visibility = ConstraintLayout.GONE
            }
            R.id.iv_btn_close_country_creation -> {
                binding.clCountryListLayout.visibility = ConstraintLayout.VISIBLE
                binding.clCountryCreationLayout.visibility = ConstraintLayout.GONE
            }
            R.id.tv_btn_create_country -> {
                createCountry()
            }
        }
    }

    private fun createCountry(){
        val validation = binding.etCountryName.text.toString().isNotEmpty() && binding.etCountryCode.text.toString().isNotEmpty() && binding.etCountryPrice.text.toString().isNotEmpty() && binding.etCountryFlag.text.toString().isNotEmpty()
        if (validation){
            countryAdminService.createCountryCategory(CreateCountry(binding.etCountryCode.text.toString(),binding.etCountryFlag.text.toString(),binding.etCountryName.text.toString(),binding.etCountryPrice.text.toString().toDouble())).enqueue(object :
                    Callback<CountryCreateResponse> {
                override fun onResponse(
                        call: Call<CountryCreateResponse>,
                        response: Response<CountryCreateResponse>
                ) {
                    Log.e("Rabee","ResponseBody ${response.body()} and responseCode ${response.code()}")
                    Log.e("Rabee","ResponseErrorBody ${response.errorBody()?.string()}")
                    when (response.code()) {
                        201 ->{
                            if (response.body() != null) {
                                val bundle=Bundle()
                                bundle.putString("catPhoneID",cPhone)
                                findNavController().navigate(R.id.createCountryFragment,bundle)
                                binding.clCountryListLayout.visibility = ConstraintLayout.VISIBLE
                                binding.clCountryCreationLayout.visibility = ConstraintLayout.GONE
                            }
                        }
                        500 -> Toast.makeText(requireContext(),"الرجاء إختيار إختصار خدمة جديد", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<CountryCreateResponse>, t: Throwable) {
                    Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                }

            })
        }else
            Toast.makeText(requireContext(),"الرجاء تعبئة الخانات المطلوبة.", Toast.LENGTH_LONG).show()
    }

}