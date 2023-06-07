package com.amk.weather.ui.mainscreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amk.weather.model.repository.currentWeather.CurrentWeatherRepository
import com.amk.weather.util.CWREx
import com.amk.weather.util.coroutineExceptionHandler
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val currentWeatherRepository: CurrentWeatherRepository
) : ViewModel() {

    val weatherInfo = mutableStateOf(CWREx)
    val showLoading = mutableStateOf(true)

    fun getWeatherInfo(lat: Double, lon: Double) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val weather = currentWeatherRepository.getCurrentWeather(lat, lon)
            if (weather.cod == 200) {
                weatherInfo.value = weather
                showLoading.value = false
            } else {
                showLoading.value = true
            }
        }
    }

}