package com.example.weatherapp.data.request

data class GetHourlyWeatherDTO(
    val latitude: Double,
    val longitude: Double,
    val hourly: String,
    val timezone: String
)
