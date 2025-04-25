package com.example.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.local.WeatherData
import com.example.weatherapp.data.response.GetForeCastResponse
import com.example.weatherapp.data.response.HourlyWeatherResponse
import com.example.weatherapp.databinding.ItemDetailBinding
import com.example.weatherapp.databinding.ItemWeatherBinding

class DetailAdapter(
    var data: HourlyWeatherResponse,
    val date: String
) : RecyclerView.Adapter<DetailAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemDetailBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount() =  filterByDate().size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            val currentDateData = combineWeatherData().filter {
                it.time.contains(date)
            }

            val time = currentDateData[position].time
            val temp = currentDateData[position].temperature
            val precipitation = currentDateData[position].precipitation
            val unitTemp = data.hourlyUnits.temperature2m.toString()
            val unitPrecip = data.hourlyUnits.precipitation.toString()


            tv1.text = extractTimeFromTimestamp(time)
            Weather.text = "Precipitation: $precipitation$unitPrecip"
            tempTv.text = "$temp$unitTemp"



        }
    }

    //Create local Weather data list
    private fun combineWeatherData(
        precipitationList: List<Double> = data.hourly.precipitation,
        temperatureList: List<Double> = data.hourly.temperature2m,
        timeList: List<String> = data.hourly.time
    ): List<WeatherData> {
        if (precipitationList.size != temperatureList.size || temperatureList.size != timeList.size) {
            throw IllegalArgumentException("All lists must have the same size")
        }

        val weatherDataList = mutableListOf<WeatherData>()

        for (i in precipitationList.indices) {
            val weatherData = WeatherData(
                precipitation = precipitationList[i],
                temperature = temperatureList[i],
                time = timeList[i]
            )
            weatherDataList.add(weatherData)
        }

        return weatherDataList
    }


    // Split  time by 'T' and take the time part
    private fun extractTimeFromTimestamp(timestamp: String): String {
        val parts = timestamp.split("T")
        return if (parts.size == 2) {
            parts[1]
        } else {
            "error"
        }
    }


    private fun filterByDate(): ArrayList<String> {
        return ArrayList(data.hourly.time.filter { it.contains(date) })
    }



}
