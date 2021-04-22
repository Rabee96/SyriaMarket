package com.example.syriamarket.interfaces

import com.example.syriamarket.pojo.*
import com.example.syriamarket.pojo.allUsers.AllUser
import com.example.syriamarket.pojo.buyNumberModel.BuyNumberModel
import com.example.syriamarket.pojo.buyProduct.BuyModelBody
import com.example.syriamarket.pojo.buyProduct.BuyProductModel
import com.example.syriamarket.pojo.cPhoneResponse.CPhoneResponse
import com.example.syriamarket.pojo.catCreatedResponse.CatCreatedResponse
import com.example.syriamarket.pojo.cats.Categories
import com.example.syriamarket.pojo.country.Country
import com.example.syriamarket.pojo.countryCreateResponse.CountryCreateResponse
import com.example.syriamarket.pojo.couponCreatedResponse.CouponCreatedResponse
import com.example.syriamarket.pojo.createPhoneCategoryResponse.CreatePhoneCategoryResponse
import com.example.syriamarket.pojo.getAllCountryPhonesResponse.GetAllCountryPhonesResponse
import com.example.syriamarket.pojo.getAllCoupons.GetAllCoupons
import com.example.syriamarket.pojo.myPurchasesModel.MyPurchases
import com.example.syriamarket.pojo.orderDetails.OrderDetails
import com.example.syriamarket.pojo.phoneCategories.PhoneCategories
import com.example.syriamarket.pojo.phoneCreateResponse.PhoneCreateResponse
import com.example.syriamarket.pojo.productCreateResponse.ProductCreateResponse
import com.example.syriamarket.pojo.products.Products
import com.example.syriamarket.pojo.refundModel.RefundModel
import com.example.syriamarket.pojo.refundResponse.RefundResponse
import com.example.syriamarket.pojo.updateToCompletedResponse.UpdateToCompletedResponse
import com.example.syriamarket.pojo.updateToDeliverResponse.UpdateToDeliverResponse
import com.example.syriamarket.pojo.updateUserBalanceResponse.UpdateUserBalanceResponse
import com.example.syriamarket.pojo.userCreatedResponse.UserCreatedResponse
import com.example.syriamarket.pojo.userInfo.UserInfo
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {
    @POST("users/login")
    fun userSignIn(@Body credentials: Credentials) : Call<User>
    @Headers("Content-Type: application/json", "Accept: application/json")
    @GET("users/me")
    fun getUser(@Header("Authorization") authHeader: String): Call<UserInfo>
    @GET("products/category")
    fun getAllCats () : Call<Categories>
    @GET("products")
    fun getAllProducts(@Query("category") category: String) : Call<Products>
    @GET("orders/getCurrentUserOrders")
    fun myPurchases():Call<MyPurchases>
    @GET("orders/getOrderByOrderId/{orderId}")
    fun getOrderDetails(@Path("orderId") orderId: String):Call<OrderDetails>
    //==============================Coupon============================================
    @POST("users/updateBalanceByCopon")
    fun buyCoupon(@Body coupon: Coupon): Call<UserInfo>
    @POST("copons")
    fun createCoupon(@Body createCoupon: CreateCoupon): Call<CouponCreatedResponse>
    @GET("copons")
    fun getAllCoupons(): Call<GetAllCoupons>
    @DELETE("copons/{couponID}")
    fun deleteCoupon(@Path("couponID") couponID: String):Call<DeletedSuccess>
    //==============================BuyProcess============================================
    @POST("orders")
    fun buyProduct(@Body buyModelBody: BuyModelBody): Call<BuyProductModel>
    //==============================Review============================================
    @PATCH("orders/{productID}/refundOrder")
    fun reviewProduct(@Path ("productID") productID: String): Call<RefundModel>
    //==============================Whatsapp============================================
    @GET("countries")
    fun getAllCountries(): Call<Country>
    @GET("phones")
    fun getAllCountriesOfCategory(@Query ("cPhone") cPhone: String): Call<CPhoneResponse>
    @POST("phones/getOrderNumbers")
    fun buyNumber(@Body buyNumberModel: BuyNumberModel): Call<BuyNumberModel>
    @GET("categoryPhone")
    fun getCategoryPhone(): Call<PhoneCategories>
    //==============================SMSHUB============================================
    @GET("handler_api.php")
    fun getNumber(@Query("api_key") api_key: String,@Query("action") action: String,@Query("service") service: String,@Query("operator") operator: String,@Query("country") country: String): Call<String>
    //https://smshub.org/stubs/handler_api.php?api_key=APIKEY&action=getStatus&id=ID GetStatus
    @GET("handler_api.php")
    fun getStatus(@Query("api_key") api_key: String,@Query("action") action: String,@Query("id") id: String): Call<String>
    //https://smshub.org/stubs/handler_api.php?api_key=APIKEY&action=setStatus&status=STATUS&id=ID Change status
    @POST("handler_api.php")
    fun setStatus(@Query("api_key") api_key: String,@Query("action") action: String,@Query("status") status: String,@Query("id") id: String): Call<String>

    //================================Admin==============================================
    @GET("users")
    fun getAllUsers():Call<AllUser>
    @POST("users/signup")
    fun createUser(@Body createUser: CreateUser):Call<UserCreatedResponse>
    @DELETE("users/{userID}")
    fun deleteUser(@Path("userID") userID: String):Call<DeletedSuccess>
    @POST("products/category")
    fun createProductCategory(@Body createCategory: CreateCategory):Call<CatCreatedResponse>
    @POST("countries")
    fun createCountryCategory(@Body createCountry: CreateCountry):Call<CountryCreateResponse>
    @POST("categoryPhone")
    fun createPhoneCategory(@Body createPhoneCategory: CreatePhoneCategory):Call<CreatePhoneCategoryResponse>
    @POST("products")
    fun createProduct(@Body createProduct: CreateProduct):Call<ProductCreateResponse>
    @POST("phones")
    fun createPhone(@Body createPhone: CreatePhone):Call<PhoneCreateResponse>
    @GET("orders?refund=true")
    fun getAllRefundsRequests():Call<RefundResponse>
    @GET("orders?refund=false&isDelivered=false&isPaid=true")
    fun getAllOnHoldRequests():Call<RefundResponse>
    @GET("orders?refund=false&isDelivered=true&isPaid=true")
    fun getAllCompletedRequests():Call<RefundResponse>
    @PATCH("orders/{productID}/deliver")
    fun updateToDeliver(@Path("productID") productID: String):Call<UpdateToDeliverResponse>
    @PATCH("orders/{productID}/orderCompleted")
    fun updateToCompleted(@Path("productID") productID: String):Call<UpdateToCompletedResponse>
    @PATCH("users/{userID}/updateBalance")
    fun updateUserBalance(@Path("userID") userID: String,@Body balance: Balance):Call<UpdateUserBalanceResponse>
    @DELETE("countries/{countryID}")
    fun deleteCountry(@Path("countryID") countryID: String):Call<DeletedSuccess>
    @GET("phones")
    fun getAllPhonesNumbers(@Query("cPhone")cPhone:String,@Query ("country")country:String):Call<GetAllCountryPhonesResponse>
}