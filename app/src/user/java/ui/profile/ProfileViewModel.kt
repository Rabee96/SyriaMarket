package ui.profile

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.Coupon
import com.example.syriamarket.pojo.userInfo.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileViewModel : ViewModel() {

    private var _userInfo = MutableLiveData<UserInfo>()
    val userInfo: LiveData<UserInfo>
        get() = _userInfo


    fun userSignedIn(token: String) {
        val userService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
        userService.getUser("Bearer $token").enqueue(object :
                Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        Log.e("RabeeProfile", response.body().toString())
                        _userInfo.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

    fun addBalance(context: Context, coupon: Coupon) {
        val couponService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
        val responseCoupon = couponService.buyCoupon(coupon)
        responseCoupon.enqueue(object :
                Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                when (response.code()) {
                    201 -> {
                        if (response.body() != null){
                            Toast.makeText(context, "تم إضافة الرصيد بنجاح.", Toast.LENGTH_LONG).show()
                        }

                    }
                    401 -> {
                        Toast.makeText(context, "هذا الكوبون غير موجود", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }


}