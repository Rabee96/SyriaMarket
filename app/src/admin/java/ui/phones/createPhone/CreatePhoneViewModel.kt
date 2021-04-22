package ui.phones.createPhone

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.getAllCountryPhonesResponse.GetAllCountryPhonesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreatePhoneViewModel : ViewModel() {

    private var  _allPhones = MutableLiveData<GetAllCountryPhonesResponse>()
    val allPhones : LiveData<GetAllCountryPhonesResponse>
        get() = _allPhones


    private val allPhonesService: ApiInterface = App.signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)
    fun responseAllPhones(cPhone: String,countryID:String){
        allPhonesService.getAllPhonesNumbers(cPhone,countryID).enqueue(object :
            Callback<GetAllCountryPhonesResponse> {
            override fun onResponse(call: Call<GetAllCountryPhonesResponse>, response: Response<GetAllCountryPhonesResponse>) {
                Log.e("RabeeAllUsers", response.body().toString())
                if (response.code() == 200) {
                    if (response.body() != null){
                        _allPhones.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<GetAllCountryPhonesResponse>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}