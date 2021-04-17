package com.example.syriamarket.pojo.orderDetails

data class Order(
    val __v: Int,
    val _id: String,
    val createdAt: String,
    val isDelivered: Boolean,
    val isPaid: Boolean,
    val orderId: String,
    val product: Product,
    val refund: Boolean,
    val totalPrice: Int,
    val whatsAppPhones: List<Any>
)