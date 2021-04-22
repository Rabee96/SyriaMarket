package ui.auth.signIn

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import base.MainActivity
import com.example.syriamarket.R
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.setToken
import com.example.syriamarket.pojo.Credentials
import com.example.syriamarket.pojo.User
import kotlinx.android.synthetic.main.fragment_sign_in.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignInFragment : Fragment() {

    private lateinit var root: View
    private lateinit var responseAuth: Call<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_sign_in, container, false)
        val service: ApiInterface = App.syriaMarketAPI.create(ApiInterface::class.java)

        root.ll_btn_sign_up.setOnClickListener {
            findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
        }
        root.btn_sign_in.setOnClickListener {
            if (root.et_email_address.text!!.trim().isEmpty() && root.et_password.text!!.trim().isEmpty()){
                root.et_email_address.error = "مطلوب"
                root.et_password.error = "مطلوب"
            }else{
                responseAuth = service.userSignIn(Credentials(root.et_email_address.text.toString(),root.et_password.text.toString()))
                responseAuth.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        when(response.code()){
                            200 -> {
                                val user = response.body() as User
                                val tknSaved = setToken(requireActivity(),user.token)
                                if (tknSaved){
                                    val intent = Intent(requireActivity(), MainActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish()
                                }
                            }
                            401 -> {
                                Toast.makeText(requireContext(),"خطأ في الايميل او كلمة المرور.",Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Log.e("Rabee", "Error :" + t.message + "\nCause" + t.cause)
                        Toast.makeText(requireContext(),"Connection error",Toast.LENGTH_LONG).show()
                    }

                })
            }

        }
        return root
    }

    override fun onResume() {
        super.onResume()
        root.ll_contact_us.setOnClickListener {
            openWhatsApp("+905527557731")
        }
    }


    private fun openWhatsApp(number: String) {
        var number = number
        try {
            number = number.replace(" ", "").replace("+", "")
            val sendIntent = Intent("android.intent.action.MAIN")
            sendIntent.component = ComponentName("com.whatsapp", "com.whatsapp.Conversation")
            sendIntent.putExtra("jid", PhoneNumberUtils.stripSeparators(number) + "@s.whatsapp.net")
            startActivity(sendIntent)
        } catch (e: Exception) {
            Log.e("Rabee", "ERROR_OPEN_MESSANGER$e")
        }
    }
}