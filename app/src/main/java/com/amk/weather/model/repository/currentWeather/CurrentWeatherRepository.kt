package com.amk.weather.model.repository.currentWeather

import com.amk.weather.model.data.CurrentWeatherResponse

interface CurrentWeatherRepository {

    suspend fun getCurrentWeather(lat: Double, lon: Double): CurrentWeatherResponse

}