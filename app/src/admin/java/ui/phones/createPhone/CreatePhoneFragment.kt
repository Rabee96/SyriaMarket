package ui.phones.createPhone

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
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.databinding.CreateCountryFragmentBinding
import com.example.syriamarket.databinding.CreatePhoneFragmentBinding
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.CreatePhone
import com.example.syriamarket.pojo.country.Country
import com.example.syriamarket.pojo.phoneCreateResponse.PhoneCreateResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ui.phones.country.CreateCountryViewModel

class CreatePhoneFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = CreatePhoneFragment()
    }

    private lateinit var viewModel: CreatePhoneViewModel
    private var _binding: CreatePhoneFragmentBinding? = null
    private val binding get() = _binding!!
    private val countryAdminService: ApiInterface = signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)
    private lateinit var cPhone: String
    private lateinit var countryID: String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val sp = requireActivity().getSharedPreferences("MyPref", Context.MODE_PRIVATE)
        cPhone = sp.getString("cPhone", "N/A")!!
        countryID = requireArguments().getString("countryID","N/A")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = CreatePhoneFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(CreatePhoneViewModel::class.java)
        binding.createPhoneViewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.responseAllPhones(cPhone,countryID)
        binding.tvBtnCreatePhone.setOnClickListener(this)
        binding.ivBtnCancelPhoneCreation.setOnClickListener(this)
        binding.ivBtnAddNewPhone.setOnClickListener(this)

        return binding.root
    }
    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_btn_create_phone -> createPhone()
            R.id.iv_btn_add_new_phone -> {
                binding.clPhoneCreationLayout.visibility = ConstraintLayout.VISIBLE
                binding.clPhoneListLayout.visibility = ConstraintLayout.GONE
            }
            R.id.iv_btn_cancel_phone_creation ->{
                val bundle = Bundle()
                bundle.putString("countryID",countryID)
                findNavController().navigate(R.id.createPhoneFragment,bundle)
            }
        }
    }

    private fun createPhone(){
        if (binding.etNewNumberCode.text.toString().isNotEmpty() && binding.etNewNumber.text.toString().isNotEmpty()){
            countryAdminService.createPhone(CreatePhone(cPhone,binding.etNewNumberCode.text.toString(),countryID,binding.etNewNumber.text.toString())).enqueue(object :
                Callback<PhoneCreateResponse> {
                override fun onResponse(call: Call<PhoneCreateResponse>, response: Response<PhoneCreateResponse>) {
                    Log.e("Rabee", response.body().toString())
                    if (response.code() == 201){
                        Toast.makeText(requireContext(),"تم إضافة الرقم",Toast.LENGTH_LONG).show()
                        binding.etNewNumber.text.clear()
                        binding.etNewNumberCode.text.clear()
                    }
                }
                override fun onFailure(call: Call<PhoneCreateResponse>, t: Throwable) {
                    Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
                }
            })
        }else
            Toast.makeText(requireContext(),"الرجاء تعبئة الخانات المطلوبة.",Toast.LENGTH_LONG).show()
    }

}