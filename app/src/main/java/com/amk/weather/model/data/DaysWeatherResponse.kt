package com.amk.weather.model.data

data class DaysWeatherResponse(
    val city: City,
    val cnt: Int,
    val cod: Int,
    val list: List<DaysWeather>,
    val message: Double
)

data class DaysWeather (
    val clouds: Int,
    val deg: Int,
    val dt: Int,
    val feels_like: FeelsLike,
    val gust: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Double,
    val speed: Double,
    val sunrise: Int,
    val sunset: Int,
    val temp: Temp,
    val weather: List<FutureWeather>
)

data class FutureWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)

data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)

data class FutureCoord(
    val lat: Double,
    val lon: Double
)

data class City(
    val coord: FutureCoord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val timezone: Int
)