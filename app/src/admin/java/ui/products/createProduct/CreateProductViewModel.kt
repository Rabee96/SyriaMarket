package ui.products.createProduct

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.products.Products
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateProductViewModel : ViewModel() {

    private var _products = MutableLiveData<Products>()
    val products: LiveData<Products>
        get() = _products
    val balance = App.getUserBalance()

    private val productsService: ApiInterface = App.signedIn(token).create(ApiInterface::class.java)
    fun getProduct(catID: String) {
        val responseProducts = productsService.getAllProducts(catID)
        responseProducts.enqueue(object :
            Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.code() == 200) {
                    if (response.body() != null)
                        _products.value = response.body()
                    Log.e("Rabee","Cat ID is : $catID \n and products is ${_products.value}")

                }
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cause : ${t.cause}")
            }

        })
    }}