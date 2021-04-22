package ui.categories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App.Companion.getUserBalance
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.cats.Categories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoriesViewModel : ViewModel() {

    private var  _cats = MutableLiveData<Categories>()
    val cats : LiveData<Categories>
        get() = _cats
    val balance = getUserBalance()
    private val catsService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
    val responseCats = catsService.getAllCats().enqueue(object :
        Callback<Categories>{
        override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
            Log.e("Rabee", response.body().toString())
            if (response.code() == 200){
                if (response.body() != null)
                    _cats.value = response.body()
            }
        }
        override fun onFailure(call: Call<Categories>, t: Throwable) {
            Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
        }

    })

}