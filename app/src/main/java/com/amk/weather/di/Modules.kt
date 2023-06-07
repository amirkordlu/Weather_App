package com.amk.weather.di

import com.amk.weather.model.net.createApiService
import com.amk.weather.model.repository.currentWeather.CurrentWeatherRepository
import com.amk.weather.model.repository.currentWeather.CurrentWeatherRepositoryImpl
import com.amk.weather.model.repository.daysWeather.DaysWeatherRepository
import com.amk.weather.model.repository.daysWeather.DaysWeatherRepositoryImpl
import com.amk.weather.model.repository.hourlyWeather.HourlyWeatherRepository
import com.amk.weather.model.repository.hourlyWeather.HourlyWeatherRepositoryImpl
import com.amk.weather.ui.daysweather.WeatherByDayViewModel
import com.amk.weather.ui.mainscreen.HourlyWeatherViewModel
import com.amk.weather.ui.mainscreen.MainScreenViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myModules = module {
    single { createApiService() }
    single<CurrentWeatherRepository> { CurrentWeatherRepositoryImpl(get()) }
    single<DaysWeatherRepository> { DaysWeatherRepositoryImpl(get()) }
    single<HourlyWeatherRepository> { HourlyWeatherRepositoryImpl(get()) }

    viewModel { MainScreenViewModel(get()) }
    viewModel { WeatherByDayViewModel(get()) }
    viewModel { HourlyWeatherViewModel(get()) }

}