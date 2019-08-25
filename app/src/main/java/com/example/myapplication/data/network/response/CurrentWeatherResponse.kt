package com.example.myapplication.data.network.response

import com.example.myapplication.data.db.entity.CurrentWeatherEntry
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    val location: Location,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)