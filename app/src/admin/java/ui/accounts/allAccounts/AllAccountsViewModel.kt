package ui.accounts.allAccounts

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.AppConstants
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.allUsers.AllUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllAccountsViewModel : ViewModel() {

    private var  _allUsers = MutableLiveData<AllUser>()
    val allUsers : LiveData<AllUser>
        get() = _allUsers
    private val allUsersService: ApiInterface = signedIn(AppConstants.ADMIN_TOKEN).create(ApiInterface::class.java)
    val responseAllUsers = allUsersService.getAllUsers().enqueue(object :
        Callback<AllUser> {
        override fun onResponse(call: Call<AllUser>, response: Response<AllUser>) {
            Log.e("RabeeAllUsers", response.body().toString())
            if (response.code() == 200){
                if (response.body() != null)
                    _allUsers.value = response.body()
            }
        }
        override fun onFailure(call: Call<AllUser>, t: Throwable) {
            Log.e("ERabee","Error : ${t.message} and cuase : ${t.cause}")
        }

    })
}