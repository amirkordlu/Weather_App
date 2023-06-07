package com.amk.weather.model.repository.daysWeather

import com.amk.weather.model.data.DaysWeatherResponse
import com.amk.weather.model.net.ApiService

class DaysWeatherRepositoryImpl(
    private val apiService: ApiService
) : DaysWeatherRepository {

    override suspend fun getDaysWeather(lat: Double, lon: Double): DaysWeatherResponse {
        return apiService.getDaysWeather(lat, lon)
    }

}