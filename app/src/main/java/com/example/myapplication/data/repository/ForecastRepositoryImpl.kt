package com.example.myapplication.data.repository

import androidx.lifecycle.LiveData
import com.example.myapplication.data.db.CurrentWeatherDao
import com.example.myapplication.data.db.unitlocalized.current.UnitSpecificCurrentWeatherEntry
import com.example.myapplication.data.network.CurrentWeatherResponse
import com.example.myapplication.data.network.response.WeatherNetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl (
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
): ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever{

        }
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {
        return withContext(Dispatchers.IO){
            initWeatherData()
            return@withContext if (metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }

    }

    private fun persistFetchedCurrentWeatcher(fetchedWeather:CurrentWeatherResponse){
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){
        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeatcher()

    }

    private suspend fun fetchCurrentWeatcher(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angeles",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchedTime:ZonedDateTime):Boolean{
        val thirtyMinutesAgo=ZonedDateTime.now().minusMinutes(30)
        return lastFetchedTime.isBefore(thirtyMinutesAgo)
    }
}