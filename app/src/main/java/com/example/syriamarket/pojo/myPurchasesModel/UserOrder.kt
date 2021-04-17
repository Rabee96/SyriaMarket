package com.example.syriamarket.pojo.myPurchasesModel

data class UserOrder(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isDelivered: Boolean,
    val isPaid: Boolean,
    val orderId: String,
    val refund: Boolean,
    val totalPrice: Int
)