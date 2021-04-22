package ui.phoneCountries

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App.Companion.getUserBalance
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.country.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PhoneCountriesViewModel : ViewModel() {
    private var  _countryUser = MutableLiveData<Country>()
    val countryUser : LiveData<Country>
        get() = _countryUser
    private val countryAdminService: ApiInterface = signedIn(token)
        .create(ApiInterface::class.java)
    val balance = getUserBalance()

    fun getCountries(){
        countryAdminService.getAllCountries().enqueue(object :
            Callback<Country> {
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                Log.e("Rabee", "responseBody =>" + response.body().toString())
                Log.e("Rabee", "responseCode =>" + response.code().toString())
                if (response.code() == 200){
                    if (response.body() != null)
                        _countryUser.value = response.body()
                }
            }
            override fun onFailure(call: Call<Country>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

}