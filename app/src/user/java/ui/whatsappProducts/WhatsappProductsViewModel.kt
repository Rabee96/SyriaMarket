package ui.whatsappProducts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.country.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WhatsappProductsViewModel : ViewModel() {

    private var  _countries = MutableLiveData<Country>()
    val countries : LiveData<Country>
        get() = _countries
    private val countryService: ApiInterface = App.signedIn(App.token).create(ApiInterface::class.java)
    val responseCountry = countryService.getAllCountries().enqueue(object :
        Callback<Country> {
        override fun onResponse(call: Call<Country>, response: Response<Country>) {
            Log.e("Rabee", response.body().toString())
            if (response.code() == 200){
                if (response.body() != null)
                    _countries.value = response.body()
            }
        }
        override fun onFailure(call: Call<Country>, t: Throwable) {
            Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
        }

    })}