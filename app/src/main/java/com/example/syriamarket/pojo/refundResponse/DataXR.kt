package com.example.syriamarket.pojo.refundResponse

data class DataXR(
        val _id: String,
        val createdAt: String,
        val isDelivered: Boolean,
        val isPaid: Boolean,
        val orderId: String,
        val phones: List<Phone>,
        val product: Product,
        val refund: Boolean,
        var totalPrice: Int,
        val user: User
)