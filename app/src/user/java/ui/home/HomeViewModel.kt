package ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.syriamarket.base.App
import com.example.syriamarket.base.App.Companion.setUserBalance
import com.example.syriamarket.base.App.Companion.signedIn
import com.example.syriamarket.base.App.Companion.token
import com.example.syriamarket.interfaces.ApiInterface
import com.example.syriamarket.pojo.HomeItemModel
import com.example.syriamarket.pojo.cats.Categories
import com.example.syriamarket.pojo.products.Products
import com.example.syriamarket.pojo.userInfo.UserInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private var _homeProducts = MutableLiveData<Products>()
    val homeProducts: LiveData<Products>
        get() = _homeProducts
    val balance = App.getUserBalance()

    var x = 0

    private var  _catsHome = MutableLiveData<Categories>()
    val catsHome : LiveData<Categories>
        get() = _catsHome

    private var  _homeModel = MutableLiveData<ArrayList<HomeItemModel>>()
    val homeModel : LiveData<ArrayList<HomeItemModel>>
        get() = _homeModel
    private var helperHome = ArrayList<HomeItemModel>()

    private val catsService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
    val responseCats = catsService.getAllCats().enqueue(object :
            Callback<Categories> {
        override fun onResponse(call: Call<Categories>, response: Response<Categories>) {
            if (response.code() == 200) {
                if (response.body() != null) {
                    _catsHome.value = response.body()
                    for (row in catsHome.value!!.data.data) {
                        val responseProducts = productsService.getAllProducts(row._id)
                        responseProducts.enqueue(object :
                                Callback<Products> {
                            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                                if (response.code() == 200) {
                                    if (response.body() != null) {
                                        _homeProducts.value = response.body()
                                        helperHome.add(HomeItemModel(row._id, row.name, homeProducts.value!!.data.data))
                                    }
                                    _homeModel.value = helperHome
                                }
                            }

                            override fun onFailure(call: Call<Products>, t: Throwable) {
                                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
                            }

                        })

                    }
                }
            }
        }

        override fun onFailure(call: Call<Categories>, t: Throwable) {
            Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
        }

    })

    private val productsService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
    fun getHomeProduct(catID: String,catName:String) {
        val responseProducts = productsService.getAllProducts(catID)
        responseProducts.enqueue(object :
                Callback<Products> {
            override fun onResponse(call: Call<Products>, response: Response<Products>) {
                if (response.code() == 200) {
                    if (response.body() != null){
                        _homeProducts.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<Products>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }

    fun userBalance(){
        val userService: ApiInterface = signedIn(token).create(ApiInterface::class.java)
        userService.getUser("Bearer $token").enqueue(object :
                Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.code() == 200) {
                    if (response.body() != null){
                        setUserBalance((response.body() as UserInfo).data.data.balance.toString())
                    }
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("ERabee", "Error : ${t.message} and cuase : ${t.cause}")
            }

        })
    }
}