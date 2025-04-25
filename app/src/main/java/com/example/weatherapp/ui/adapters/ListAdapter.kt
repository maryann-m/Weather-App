package com.example.weatherapp.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.data.response.GetForeCastResponse
import com.example.weatherapp.databinding.ItemWeatherBinding
import retrofit2.http.GET

class ListAdapter(
    var data: GetForeCastResponse, private val onItemClick: (String) -> Unit,
) : RecyclerView.Adapter<ListAdapter.ViewHolder>() {


    inner class ViewHolder(val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    //View Binding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemWeatherBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun getItemCount() = data.daily.time.size

    //Populate UI
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {

            val units = data.dailyUnits.temperature2mMax.toString()
            val dates = data.daily.time[position]
            val max = data.daily.temperature2mMax[position]
            val min = data.daily.temperature2mMin[position]
            val weather = data.daily.weathercode[position]

            tv1.text = dates
            Weather.text = getWeatherDescription(weather)
            temp.text = "$max$units/$min$units"
            root.setOnClickListener {
                onItemClick(dates)

            }


        }
    }


    private fun getWeatherDescription(code: Int): String {
        return when (code) {
            0 -> "Clear sky"
            1 -> "Mainly clear"
            2 -> "Partly cloudy"
            3 -> "Overcast"
            45 -> "Fog"
            48 -> "Depositing rime fog"
            51 -> "Light drizzle"
            53 -> "Moderate drizzle"
            55 -> "Dense drizzle"
            61 -> "Slight rain"
            63 -> "Moderate rain"
            65 -> "Heavy rain"
            80 -> "Slight rain showers"
            81 -> "Moderate rain showers"
            82 -> "Violent rain showers"
            else -> "Unknown weather"
        }
    }

}

