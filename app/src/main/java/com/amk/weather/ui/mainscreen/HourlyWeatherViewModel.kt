package com.amk.weather.ui.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.weather.model.repository.hourlyWeather.HourlyWeatherRepository
import com.amk.weather.util.HWREx
import com.amk.weather.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class HourlyWeatherViewModel(
    private val hourlyWeatherRepository: HourlyWeatherRepository,
) : ViewModel() {

    val hourlyWeather = mutableStateOf(HWREx)
    val showLoading = mutableStateOf(true)

    fun getHourlyWeather() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val weather = hourlyWeatherRepository.getHourlyWeather()
            if (weather.cod == 200) {
                hourlyWeather.value = weather
                showLoading.value = false
            } else {
                showLoading.value = true
            }
        }
    }

}