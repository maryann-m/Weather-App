package com.example.weatherapp.repo

import com.example.weatherapp.data.request.GetForeCastDTO
import com.example.weatherapp.data.request.GetHourlyWeatherDTO
import com.example.weatherapp.network.ApiService
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ApiRepository @Inject constructor(
    private val apiService: ApiService,

) {



    //function to get forecast to return flow for asynch requests
    suspend fun getForeCast(data: GetForeCastDTO) =
  flowOf(apiService.getWeatherForecast(data.latitude, data.longitude, data.daily, data.timezone))


    //get hourly weather
    suspend fun getHourlyWeather(data: GetHourlyWeatherDTO) =
        flowOf(apiService.getHourlyWeather(data.latitude, data.longitude, data.hourly, data.timezone))

}