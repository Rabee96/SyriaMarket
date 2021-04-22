package ui.accounts.createCoupon

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.AppConstants.Companion.ADMIN_TOKEN
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.DeletedSuccess
import com.example.syriamarket.pojo.getAllCoupons.GetAllCoupons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateCouponViewModel : ViewModel() {
    private var  _allCoupons = MutableLiveData<GetAllCoupons>()
    val allCoupons : LiveData<GetAllCoupons>
        get() = _allCoupons

    var couponGenerated = MutableLiveData<String>()
    var couponBalance = MutableLiveData<String>()

    val allCouponsService: ApiInterface = App.signedIn(ADMIN_TOKEN).create(ApiInterface::class.java)
    fun responseAllCoupons(){
        allCouponsService.getAllCoupons().enqueue(object :
                Callback<GetAllCoupons> {
            override fun onResponse(call: Call<GetAllCoupons>, response: Response<GetAllCoupons>) {
                Log.e("RabeeAllUsers", response.body().toString())
                if (response.code() == 200) {
                    if (response.body() != null){
                        _allCoupons.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllCoupons>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }



    fun deleteCoupon(context: Context, couponID:String){
        allCouponsService.deleteCoupon(couponID).enqueue(object :
                Callback<DeletedSuccess> {
            override fun onResponse(
                    call: Call<DeletedSuccess>,
                    response: Response<DeletedSuccess>
            ) {
                when (response.code()) {
                    200 ->{
                        if (response.body() != null) {
                            Toast.makeText(context,"تم حذف الكوبون.", Toast.LENGTH_LONG).show()
                        }
                    }
                    500 -> Toast.makeText(context,"حدث خطأ ما.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<DeletedSuccess>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

}