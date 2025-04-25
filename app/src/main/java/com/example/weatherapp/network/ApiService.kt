package com.example.weatherapp.network

import com.example.weatherapp.data.response.GetForeCastResponse
import com.example.weatherapp.data.response.HourlyWeatherResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String,
        @Query("timezone") timezone: String
    ): Response<GetForeCastResponse>


    @GET("forecast")
    suspend fun getHourlyWeather(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String,
        @Query("timezone") timezone: String
    ): Response<HourlyWeatherResponse>

}