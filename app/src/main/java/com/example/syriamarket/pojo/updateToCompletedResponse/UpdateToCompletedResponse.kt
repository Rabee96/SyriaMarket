package com.example.syriamarket.pojo.updateToCompletedResponse

data class UpdateToCompletedResponse(
    val message: String,
    val order: Order,
    val status: String
)