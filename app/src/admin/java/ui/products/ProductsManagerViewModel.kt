package ui.products

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.cats.Categories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsManagerViewModel : ViewModel() {

    private var  _catsAdmin = MutableLiveData<Categories>()
    val catsAdmin : LiveData<Categories>
        get() = _catsAdmin
    val balance = App.getUserBalance()
    private val catsAdminService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
    val responseCatsAdmin = catsAdminService.getAllCats().enqueue(object :
        Callback<Categories> {
        override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
            Log.e("Rabee", response.body().toString())
            if (response.code() == 200){
                if (response.body() != null)
                    _catsAdmin.value = response.body()
            }
        }
        override fun onFailure(call: Call<Categories>, t: Throwable) {
            Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
        }

    })
}