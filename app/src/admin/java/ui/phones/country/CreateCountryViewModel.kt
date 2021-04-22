package ui.phones.country

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.DeletedSuccess
import com.example.syriamarket.pojo.cPhoneResponse.CPhoneResponse
import com.example.syriamarket.pojo.country.Country
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateCountryViewModel : ViewModel() {
    private var  _countryAdmin = MutableLiveData<Country>()
    val countryAdmin : LiveData<Country>
        get() = _countryAdmin
    private val countryAdminService: ApiInterface = signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)

    fun getCountries(){
        countryAdminService.getAllCountries().enqueue(object :
            Callback<Country> {
            override fun onResponse(call: Call<Country>, response: Response<Country>) {
                Log.e("Rabee", "responseBody =>" + response.body().toString())
                Log.e("Rabee", "responseCode =>" + response.code().toString())
                if (response.code() == 200){
                    if (response.body() != null)
                        _countryAdmin.value = response.body()
                }
            }
            override fun onFailure(call: Call<Country>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

    fun deleteCountry(countryID: String){
        countryAdminService.deleteCountry(countryID).enqueue(object :
                Callback<DeletedSuccess> {
            override fun onResponse(call: Call<DeletedSuccess>, response: Response<DeletedSuccess>) {
                Log.e("Rabee", "responseBody =>" + response.body().toString())
                Log.e("Rabee", "responseCode =>" + response.code().toString())
                if (response.code() == 200){
                    if (response.body() != null){

                    }
                }
            }
            override fun onFailure(call: Call<DeletedSuccess>, t: Throwable) {
                Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}