package com.amk.weather.model.repository.hourlyWeather

import com.amk.weather.model.data.HourlyWeatherResponse

interface HourlyWeatherRepository {

    suspend fun getHourlyWeather(): HourlyWeatherResponse

}