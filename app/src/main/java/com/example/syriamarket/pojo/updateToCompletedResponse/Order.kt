package com.example.syriamarket.pojo.updateToCompletedResponse

data class Order(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isDelivered: Boolean,
    val isPaid: Boolean,
    val orderId: String,
    val phones: List<Any>,
    val product: Product,
    val refund: Boolean,
    val totalPrice: Int,
    val user: User
)