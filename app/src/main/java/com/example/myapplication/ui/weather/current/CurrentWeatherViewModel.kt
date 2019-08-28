package com.example.myapplication.ui.weather.current


import androidx.lifecycle.ViewModel;
import com.example.myapplication.data.repository.ForecastRepository
import com.example.myapplication.internal.UnitSystem
import com.example.myapplication.internal.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    private val unitSystem = UnitSystem.METRIC //get from settings later

    val isMetric: Boolean
        get() = unitSystem == UnitSystem.METRIC

    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather(isMetric)
    }
}