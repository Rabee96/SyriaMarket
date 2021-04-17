package com.example.syriamarket.interfaces

import com.example.syriamarket.pojo.Coupon
import com.example.syriamarket.pojo.Credentials
import com.example.syriamarket.pojo.User
import com.example.syriamarket.pojo.buyProduct.BuyModelBody
import com.example.syriamarket.pojo.buyProduct.BuyProductModel
import com.example.syriamarket.pojo.cats.Categories
import com.example.syriamarket.pojo.myPurchasesModel.MyPurchases
import com.example.syriamarket.pojo.orderDetails.OrderDetails
import com.example.syriamarket.pojo.products.Products
import com.example.syriamarket.pojo.refundModel.RefundModel
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
    //==============================BuyProcess============================================
    @POST("orders")
    fun buyProduct(@Body buyModelBody: BuyModelBody): Call<BuyProductModel>
    //==============================Review============================================
    @PATCH("orders/{productID}/refundOrder")
    fun reviewProduct(@Path ("productID") productID: String): Call<RefundModel>
}