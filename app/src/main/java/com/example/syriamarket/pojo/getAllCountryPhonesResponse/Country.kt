package com.example.syriamarket.pojo.getAllCountryPhonesResponse

data class Country(
    val _id: String,
    val countryCode: String,
    val createdAt: String,
    val flag: String,
    val name: String,
    val price: Double
)