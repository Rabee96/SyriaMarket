package ui.whatsappCats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.cats.Categories
import com.example.syriamarket.pojo.phoneCategories.PhoneCategories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WhatsAppCatsViewModel : ViewModel() {
    private var  _catsWhatsApp = MutableLiveData<PhoneCategories>()
    val catsWhatsApp : LiveData<PhoneCategories>
        get() = _catsWhatsApp
    val balanceWhatsApp = App.getUserBalance()
    private val catsWhatsAppService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
    val responseCatsWhatsApp = catsWhatsAppService.getCategoryPhone().enqueue(object :
            Callback<PhoneCategories> {
        override fun onResponse(call: Call<PhoneCategories>, response: Response<PhoneCategories>) {
            Log.e("Rabee", response.body().toString())
            if (response.code() == 200){
                if (response.body() != null)
                    _catsWhatsApp.value = response.body()
            }
        }
        override fun onFailure(call: Call<PhoneCategories>, t: Throwable) {
            Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
        }

    })}