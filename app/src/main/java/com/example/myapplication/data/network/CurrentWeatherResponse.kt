package com.example.myapplication.data.network

import com.example.myapplication.data.db.entity.CurrentWeatherEntry
import com.example.myapplication.data.db.entity.WeatherLocation

import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    val location: WeatherLocation,
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry
)