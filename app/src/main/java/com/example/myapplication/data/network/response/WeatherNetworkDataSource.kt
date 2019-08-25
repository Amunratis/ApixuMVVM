package com.example.myapplication.data.network.response

import androidx.lifecycle.LiveData
import com.example.myapplication.data.network.CurrentWeatherResponse

interface WeatherNetworkDataSource {
    val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String,
        languageCode: String
    )
}