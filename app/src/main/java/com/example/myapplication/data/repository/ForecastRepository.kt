package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    suspend fun getCurrentWeather(metric:Boolean ):LiveData<out UnitSpecificCurrentWeatherEntry>
}