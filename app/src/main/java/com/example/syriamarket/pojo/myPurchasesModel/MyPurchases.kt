package com.example.syriamarket.pojo.myPurchasesModel

data class MyPurchases(
    val results: Int,
    val status: String,
    val userOrders: ArrayList<UserOrder>
)