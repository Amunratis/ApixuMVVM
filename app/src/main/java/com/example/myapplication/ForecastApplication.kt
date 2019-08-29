package com.example.myapplication

import android.app.Application
import android.content.Context

import android.preference.PreferenceManager
import com.example.myapplication.data.ApixuWeatherApiService
import com.example.myapplication.data.db.ForecastDatabase
import com.example.myapplication.data.network.response.WeatherNetworkDataSource
import com.example.myapplication.data.network.response.WeatherNetworkDataSourceImpl
import com.example.myapplication.data.provider.LocationProvider
import com.example.myapplication.data.provider.LocationProviderImpl
import com.example.myapplication.data.provider.UnitProvider
import com.example.myapplication.data.provider.UnitProviderImpl
import com.example.myapplication.data.repository.ForecastRepository
import com.example.myapplication.data.repository.ForecastRepositoryImpl
import com.example.myapplication.ui.weather.current.CurrentWeatherViewModelFactory
import com.google.android.gms.location.LocationServices
import com.jakewharton.threetenabp.AndroidThreeTen
import com.resocoder.forecastmvvm.data.network.ConnectivityInterceptor
import com.resocoder.forecastmvvm.data.network.ConnectivityInterceptorImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind() from singleton { instance<ForecastDatabase>().weatherLocationDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { ApixuWeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance(), instance(), instance()) }
        bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance(), instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false)
    }
}