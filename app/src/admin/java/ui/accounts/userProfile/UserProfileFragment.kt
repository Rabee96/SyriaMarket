package ui.accounts.userProfile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ScrollView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.syriamarket.R
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.databinding.UserProfileFragmentBinding
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.Balance
import com.example.syriamarket.pojo.DeletedSuccess
import com.example.syriamarket.pojo.countryCreateResponse.CountryCreateResponse
import com.example.syriamarket.pojo.updateUserBalanceResponse.UpdateUserBalanceResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserProfileFragment : Fragment(),View.OnClickListener {

    companion object {
        fun newInstance() = UserProfileFragment()
    }

    private lateinit var viewModel: UserProfileViewModel
    private var _binding: UserProfileFragmentBinding? = null
    private val binding get() = _binding!!
    private val userProfileAdminService: ApiInterface =
            signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)
    private lateinit var userID:String
    private lateinit var userName:String
    private lateinit var userEmail:String
    private lateinit var userRank:String
    private lateinit var userRole:String
    private var userBalance = 0
    private lateinit var userAddress:String
    private lateinit var userPhone:String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        userID = requireArguments().getString("userID", "N/A")
        userName = requireArguments().getString("userName", "N/A")
        userEmail = requireArguments().getString("userEmail", "N/A")
        userRank = requireArguments().getString("userRank", "N/A")
        userRole = requireArguments().getString("userRole", "N/A")
        userBalance = requireArguments().getInt("userBalance", -1)
        userAddress = requireArguments().getString("userAddress", "N/A")
        userPhone = requireArguments().getString("userPhone", "N/A")
    }
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = UserProfileFragmentBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(UserProfileViewModel::class.java)
        binding.userProfileViewModel = viewModel
        binding.lifecycleOwner = this
        binding.btnDeleteUserAdmin.setOnClickListener(this)
        binding.btnEditBalanceAdmin.setOnClickListener(this)
        binding.ivBtnCloseAddBalanceProcess.setOnClickListener(this)
        binding.tvBtnAddBalance.setOnClickListener(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.tvUserNameAdmin.text = userName
        binding.tvUserRank.text = userRank
        binding.etUserEmailAddressAdmin.setText(userEmail)
        binding.etUserRoleAdmin.setText(userRole)
        binding.etUserCodeAdmin.setText(userBalance.toString())
        binding.etUserLocationAdmin.setText(userAddress)
        binding.etUserPhoneAdmin.setText(userPhone)

    }


    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.tv_btn_add_balance -> {
                addBalance(userID)
            }
            R.id.btn_delete_user_admin -> {
                deleteUser(userID)
            }
            R.id.btn_edit_balance_admin -> {
                binding.clAddBalanceLayout.visibility = ConstraintLayout.VISIBLE
                binding.svProfileLayout.visibility = ScrollView.GONE
                binding.btnEditBalanceAdmin.visibility = Button.GONE
                binding.tvOldBalance.text = userBalance.toString()
            }
            R.id.iv_btn_close_add_balance_process -> {
                binding.svProfileLayout.visibility = ScrollView.VISIBLE
                binding.btnEditBalanceAdmin.visibility = Button.VISIBLE
                binding.clAddBalanceLayout.visibility = ConstraintLayout.GONE
            }
        }
    }

    private fun addBalance(userID: String){
        userProfileAdminService.updateUserBalance(userID, Balance(binding.etNewAddBalance.text.toString().toInt())).enqueue(object :
                Callback<UpdateUserBalanceResponse> {
            override fun onResponse(
                    call: Call<UpdateUserBalanceResponse>,
                    response: Response<UpdateUserBalanceResponse>
            ) {
                when (response.code()) {
                    201 ->{
                        if (response.body() != null) {
                            binding.etUserCodeAdmin.setText((userBalance +binding.etNewAddBalance.text.toString().toInt()).toString())
                            Toast.makeText(requireContext(),"تم إضافة الرصيد بنجاح.", Toast.LENGTH_LONG).show()
                            binding.svProfileLayout.visibility = ScrollView.VISIBLE
                            binding.btnEditBalanceAdmin.visibility = Button.VISIBLE
                            binding.clAddBalanceLayout.visibility = ConstraintLayout.GONE
                        }
                    }
                    500 -> Toast.makeText(requireContext(),"حدث خطأ ما.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UpdateUserBalanceResponse>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

    private fun deleteUser(userID:String){
        userProfileAdminService.deleteUser(userID).enqueue(object :
                Callback<DeletedSuccess> {
            override fun onResponse(
                    call: Call<DeletedSuccess>,
                    response: Response<DeletedSuccess>
            ) {
                when (response.code()) {
                    200 ->{
                        if (response.body() != null) {
                            Toast.makeText(requireContext(),"تم حذف المستخدم.", Toast.LENGTH_LONG).show()
                            findNavController().navigate(R.id.action_userProfileFragment_to_nav_manage_accounts)
                        }
                    }
                    500 -> Toast.makeText(requireContext(),"حدث خطأ ما.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<DeletedSuccess>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}