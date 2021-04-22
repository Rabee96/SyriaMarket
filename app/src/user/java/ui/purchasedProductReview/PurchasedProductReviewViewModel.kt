package ui.purchasedProductReview

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.orderDetails.OrderDetails
import com.example.syriamarket.pojo.refundModel.RefundModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PurchasedProductReviewViewModel : ViewModel() {

    private var _orderDetails = MutableLiveData<OrderDetails>()
    val orderDetails : LiveData<OrderDetails>
        get() = _orderDetails
    fun details (orderID: String){
        val reviewService: ApiInterface = App.signedIn(App.token).create(ApiInterface::class.java)
        val responseReview = reviewService.getOrderDetails(orderID).enqueue(object :
                Callback<OrderDetails> {
            override fun onResponse(call: Call<OrderDetails>, response: Response<OrderDetails>) {
                Log.e("RabeeReview",response.body().toString() + "code" + response.code() + " message" + response.message())
                if (response.code() == 201){
                    if (response.body() != null)
                        _orderDetails.value = response.body()
                }
            }
            override fun onFailure(call: Call<OrderDetails>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }
        })
    }

    fun reviewRequest (context: Context,productID: String){
        val reviewService: ApiInterface = App.signedIn(App.token).create(ApiInterface::class.java)
        reviewService.reviewProduct(productID).enqueue(object :
                Callback<RefundModel> {
            override fun onResponse(call: Call<RefundModel>, response: Response<RefundModel>) {
                Log.e("RabeeReview",response.body().toString() + "code" + response.code() + " message" + response.message())
                if (response.code() == 201){
                    if (response.body() != null)
                        Toast.makeText(context,"جاري مراجعة الطلب.",Toast.LENGTH_LONG).show()
                }
            }
            override fun onFailure(call: Call<RefundModel>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }
        })
    }


}