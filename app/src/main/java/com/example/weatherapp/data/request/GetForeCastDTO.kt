package com.example.weatherapp.data.request


data class GetForeCastDTO(
    val latitude: Double,
    val longitude: Double,
    val daily: String,
    val timezone: String
)
