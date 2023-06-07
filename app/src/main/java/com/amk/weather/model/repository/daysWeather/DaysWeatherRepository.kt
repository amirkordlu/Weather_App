package com.amk.weather.model.repository.daysWeather

import com.amk.weather.model.data.DaysWeatherResponse

interface DaysWeatherRepository {

    suspend fun getDaysWeather(lat: Double, lon: Double): DaysWeatherResponse

}