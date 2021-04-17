package com.example.syriamarket.pojo.buyProduct

data class CreatedOrder(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isDelivered: Boolean,
    val isPaid: Boolean,
    val orderId: String,
    val product: String,
    val refund: Boolean,
    val totalPrice: Int,
    val user: String,
    val whatsAppPhones: List<Any>
)