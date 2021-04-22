package ui.refund

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.refundResponse.RefundResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RefundViewModel : ViewModel() {

    private var  _refundsAdmin = MutableLiveData<RefundResponse>()
    val refundsAdmin : LiveData<RefundResponse>
        get() = _refundsAdmin
    private val refundsAdminService: ApiInterface = signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)

    fun refundRequests(){
        refundsAdminService.getAllRefundsRequests().enqueue(object :
                Callback<RefundResponse> {
            override fun onResponse(call: Call<RefundResponse>, response: Response<RefundResponse>) {
                if (response.code() == 200){
                    if (response.body() != null)
                        _refundsAdmin.value = response.body()
                }
            }
            override fun onFailure(call: Call<RefundResponse>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
    fun completedRequests(){
        refundsAdminService.getAllCompletedRequests().enqueue(object :
                Callback<RefundResponse> {
            override fun onResponse(call: Call<RefundResponse>, response: Response<RefundResponse>) {
                if (response.code() == 200){
                    if (response.body() != null)
                        _refundsAdmin.value = response.body()
                }
            }
            override fun onFailure(call: Call<RefundResponse>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
    fun onHoldRequests(){
        refundsAdminService.getAllOnHoldRequests().enqueue(object :
                Callback<RefundResponse> {
            override fun onResponse(call: Call<RefundResponse>, response: Response<RefundResponse>) {
                if (response.code() == 200){
                    if (response.body() != null)
                        _refundsAdmin.value = response.body()
                }
            }
            override fun onFailure(call: Call<RefundResponse>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}