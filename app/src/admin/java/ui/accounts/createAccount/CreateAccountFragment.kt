package ui.accounts.createAccount

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.syriamarket.R
import com.example.syriamarket.base.App.Companion.syriaMarketAPI
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.CreateUser
import com.example.syriamarket.pojo.userCreatedResponse.UserCreatedResponse
import kotlinx.android.synthetic.admin.create_account_fragment.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountFragment : Fragment() {

    companion object {
        fun newInstance() = CreateAccountFragment()
    }

    private lateinit var viewModel: CreateAccountViewModel
    val service: ApiInterface = syriaMarketAPI.create(ApiInterface::class.java)
    private lateinit var responseCreateUser: Call<UserCreatedResponse>
    private lateinit var root: View
    private lateinit var addressView:EditText
    private lateinit var emailView:EditText
    private lateinit var nameView:EditText
    private lateinit var passwordView:EditText
    private lateinit var passwordConfirmView:EditText
    private lateinit var phoneView:EditText
    private lateinit var roleView:RadioGroup
    private lateinit var userTypeView:RadioGroup
    private lateinit var btnCreateUser: Button
    private var userRole = "user"
    private var userType = "A"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.create_account_fragment, container, false)
        addressView = root.et_sign_up_address
        emailView = root.et_sign_up_email_address
        nameView = root.et_sign_up_user_name
        passwordView = root.et_sign_up_password
        passwordConfirmView = root.et_sign_up_repeat_password
        phoneView = root.et_sign_up_phone
        roleView = root.rg_user_role
        userTypeView = root.rg_user_type
        btnCreateUser= root.btn_create_user
        return root
    }
    override fun onResume() {
        super.onResume()
        roleView.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_customer -> userRole = "user"
                R.id.rb_admin -> userRole = "admin"
            }
        }
        userTypeView.setOnCheckedChangeListener { _, checkedId ->
            when(checkedId){
                R.id.rb_user_type_a -> userType = "A"
                R.id.rb_user_type_b -> userType = "B"
                R.id.rb_user_type_c -> userType = "C"
            }
        }
        btnCreateUser.setOnClickListener {
            val validation = addressView.text.toString().trim().isNotEmpty() && emailView.text.toString().trim().isNotEmpty() && nameView.text.toString().trim().isNotEmpty() && passwordView.text.toString().trim().isNotEmpty() && passwordConfirmView.text.toString().trim().isNotEmpty() && phoneView.text.toString().trim().isNotEmpty() && userRole.isNotEmpty() && userType.isNotEmpty() && (passwordView.text.toString().trim() == passwordConfirmView.text.toString().trim())
            if (validation){
                when{
                    passwordView.text.toString().length < 8 ->{
                        passwordView.error = "يجب ان تكون أطول من 8 أحرف أو أرقام."
                        Toast.makeText(requireContext(),"كلمة المرور قصير جداً",Toast.LENGTH_LONG).show()

                    }
                    emailView.text.toString().length < 8 ->{
                        emailView.error = "البريد الإلكتروني قصير جداً."
                        Toast.makeText(requireContext(),"كلمة المرور قصير جدا",Toast.LENGTH_LONG).show()
                    }
                    else -> createUser(addressView.text.toString(),emailView.text.toString(),nameView.text.toString(),passwordView.text.toString(),passwordConfirmView.text.toString(),phoneView.text.toString(),userRole,userType)
                }
            }
            else{
                Toast.makeText(requireContext(),"الرجاء إكمال الخانات المطلوبة",Toast.LENGTH_LONG).show()
                if (addressView.text.toString().trim().isEmpty())
                    addressView.error = resources.getString(R.string.required)
                if (emailView.text.toString().trim().isEmpty())
                    emailView.error = resources.getString(R.string.required)
                if (nameView.text.toString().trim().isEmpty())
                    nameView.error = resources.getString(R.string.required)
                if (passwordView.text.toString().trim().isEmpty())
                    passwordView.error = resources.getString(R.string.required)
                if (passwordConfirmView.text.toString().trim().isEmpty())
                    passwordConfirmView.error = resources.getString(R.string.required)
                if (phoneView.text.toString().trim().isEmpty())
                    phoneView.error = resources.getString(R.string.required)
                if ((passwordView.text.toString().trim() != passwordConfirmView.text.toString().trim()))
                    phoneView.error = resources.getString(R.string.password_not_matched)
            }
        }
    }
    private fun createUser(address:String, email:String, name:String, password:String, passwordConfirm:String, phone:String, role:String, userType:String){
        responseCreateUser = service.createUser(CreateUser(address,email,name,password,passwordConfirm,phone,role,userType))
        responseCreateUser.enqueue(object : Callback<UserCreatedResponse> {
            override fun onResponse(call: Call<UserCreatedResponse>, response: Response<UserCreatedResponse>) {
                Log.e("Rabee","ResponseBody ${response.body()} and responseCode ${response.code()}")
                Log.e("Rabee","ResponseErrorBody ${response.errorBody()?.string()}")
                when(response.code()){
                    201 -> {
                        Toast.makeText(requireContext(),"تم إنشاء الحساب بنجاح.", Toast.LENGTH_LONG).show()
                        findNavController().navigate(R.id.nav_manage_accounts)
                    }
                    401 -> {
                        Toast.makeText(requireContext(),"خطأ في الايميل او كلمة المرور.", Toast.LENGTH_LONG).show()
                    }
                }
            }
            override fun onFailure(call: Call<UserCreatedResponse>, t: Throwable) {
                Log.e("Rabee", "Error :" + t.message + "\nCause" + t.cause)
                Toast.makeText(requireContext(),"Connection error", Toast.LENGTH_LONG).show()
            }

        })
    }
}