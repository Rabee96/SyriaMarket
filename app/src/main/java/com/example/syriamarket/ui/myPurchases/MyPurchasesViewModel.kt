package com.example.syriamarket.ui.myPurchases

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.myPurchasesModel.MyPurchases
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPurchasesViewModel : ViewModel() {
    private var  _orders = MutableLiveData<MyPurchases>()
    val orders : LiveData<MyPurchases>
        get() = _orders
    val balance = App.getUserBalance()

    private val ordersService: ApiInterface = App.signedIn(App.token).create(ApiInterface::class.java)
    val responseOrders = ordersService.myPurchases().enqueue(object :
            Callback<MyPurchases> {
        override fun onResponse(call: Call<MyPurchases>, response: Response<MyPurchases>) {
            if (response.code() == 201){
                if (response.body() != null)
                    _orders.value = response.body()
            }
        }
        override fun onFailure(call: Call<MyPurchases>, t: Throwable) {
            Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
        }
    })

}