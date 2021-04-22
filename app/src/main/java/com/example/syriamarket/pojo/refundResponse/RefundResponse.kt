package com.example.syriamarket.pojo.refundResponse

data class RefundResponse(
    val `data`: Data,
    val requestedAt: String,
    val results: Int,
    val status: String
)