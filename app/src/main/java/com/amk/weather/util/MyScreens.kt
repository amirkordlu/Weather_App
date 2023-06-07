package com.amk.weather.util

sealed class MyScreens(val route: String) {
    object MainScreen : MyScreens("mainScreen")
    object WeatherScreen : MyScreens("weatherByDay")
}

