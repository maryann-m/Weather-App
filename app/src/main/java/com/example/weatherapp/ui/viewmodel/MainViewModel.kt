package com.example.weatherapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.data.local.WeatherData
import com.example.weatherapp.network.ApiResponse
import com.example.weatherapp.data.request.GetForeCastDTO
import com.example.weatherapp.data.request.GetHourlyWeatherDTO
import com.example.weatherapp.data.response.GetForeCastResponse
import com.example.weatherapp.data.response.HourlyWeatherResponse
import com.example.weatherapp.repo.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: ApiRepository,
) : ViewModel() {


    private val _getForeCastState: MutableSharedFlow<ApiResponse<GetForeCastResponse>> = MutableSharedFlow()
    val getForeCast: SharedFlow<ApiResponse<GetForeCastResponse>> = _getForeCastState.asSharedFlow()

    private val _getHourlyWeather: MutableSharedFlow<ApiResponse<HourlyWeatherResponse>> = MutableSharedFlow()
    val getHourlyWeather: SharedFlow<ApiResponse<HourlyWeatherResponse>> = _getHourlyWeather.asSharedFlow()

    fun getForeCast(data: GetForeCastDTO) {

        viewModelScope.launch {
            try {
                _getForeCastState.emit(ApiResponse.loading(null))
                val response = repo.getForeCast(data)
                response.collectLatest {

                    if (it.code() == 200) {
                        _getForeCastState.emit(ApiResponse.success(it.body()))
                    } else {
                        _getForeCastState.emit(
                            ApiResponse.error(
                                "Failed",
                                it.body()
                            )
                        )

                    }
                }

            } catch (t: Throwable) {
                _getForeCastState.emit(
                    ApiResponse.error(
                        "Failed",
                        null
                    )
                )

                println(t.message)
            }

        }
    }


    fun getHourlyWeather(data: GetHourlyWeatherDTO) {

        viewModelScope.launch {
            try {
                _getHourlyWeather.emit(ApiResponse.loading(null))
                val response = repo.getHourlyWeather(data)
                response.collectLatest {

                    if (it.code() == 200) {
                        _getHourlyWeather.emit(ApiResponse.success(it.body()))
                    } else {
                        _getHourlyWeather.emit(
                            ApiResponse.error(
                                "Failed",
                                it.body()
                            )
                        )

                    }
                }

            } catch (t: Exception) {
                _getHourlyWeather.emit(
                    ApiResponse.error(
                        "Failed",
                        null
                    )
                )

                println(t.localizedMessage)
            }

        }
    }









}