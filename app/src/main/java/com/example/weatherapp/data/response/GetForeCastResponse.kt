package com.example.weatherapp.data.response

import com.google.gson.annotations.SerializedName

data class GetForeCastResponse(
    @SerializedName("latitude"              ) var latitude             : Double?     = null,
    @SerializedName("longitude"             ) var longitude            : Double?     = null,
    @SerializedName("generationtime_ms"     ) var generationtimeMs     : Double?     = null,
    @SerializedName("utc_offset_seconds"    ) var utcOffsetSeconds     : Int?        = null,
    @SerializedName("timezone"              ) var timezone             : String?     = null,
    @SerializedName("timezone_abbreviation" ) var timezoneAbbreviation : String?     = null,
    @SerializedName("elevation"             ) var elevation            : Int?        = null,
    @SerializedName("daily_units"           ) var dailyUnits           : DailyUnits = DailyUnits(),
    @SerializedName("daily"                 ) var daily                : Daily      = Daily()

)

data class DailyUnits (
    @SerializedName("time"               ) var time             : String? = null,
    @SerializedName("weathercode"        ) var weathercode      : String? = null,
    @SerializedName("temperature_2m_max" ) var temperature2mMax : String? = null,
    @SerializedName("temperature_2m_min" ) var temperature2mMin : String? = null
)


data class Daily (
    @SerializedName("time"               ) var time             : ArrayList<String> = arrayListOf(),
    @SerializedName("weathercode"        ) var weathercode      : ArrayList<Int>    = arrayListOf(),
    @SerializedName("temperature_2m_max" ) var temperature2mMax : ArrayList<Double> = arrayListOf(),
    @SerializedName("temperature_2m_min" ) var temperature2mMin : ArrayList<Double> = arrayListOf()

)