package com.amk.weather.model.repository.hourlyWeather

import com.amk.weather.model.data.HourlyWeatherResponse
import com.amk.weather.model.net.ApiService

class HourlyWeatherRepositoryImpl(
    private val apiService: ApiService
) : HourlyWeatherRepository {

    override suspend fun getHourlyWeather(lat: Double, lon: Double): HourlyWeatherResponse {
        return apiService.getHourlyWeather(lat, lon)
    }

}