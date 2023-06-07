package com.amk.weather.ui.daysweather

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.weather.model.repository.daysWeather.DaysWeatherRepository
import com.amk.weather.util.DWREx
import com.amk.weather.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class WeatherByDayViewModel(
    private val daysWeatherRepository: DaysWeatherRepository,
) : ViewModel() {

    val weatherByDay = mutableStateOf(DWREx)
    val showLoading = mutableStateOf(true)

    fun getWeatherByDay() {
        viewModelScope.launch(coroutineExceptionHandler) {
            val weather = daysWeatherRepository.getDaysWeather()
            if (weather.cod == 200) {
                weatherByDay.value = weather
                showLoading.value = false
            } else {
                showLoading.value = true
            }
        }
    }

}