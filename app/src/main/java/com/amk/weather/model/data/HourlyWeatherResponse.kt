package com.amk.weather.model.data

data class HourlyWeatherResponse(
    val city: HourlyWeatherCity,
    val cnt: Int,
    val cod: Int,
    val list: List<HourlyWeather>,
    val message: Int
)

data class HourlyWeather(
    val clouds: HourlyWeatherClouds,
    val dt: Int,
    val dt_txt: String,
    val main: HourlyWeatherMain,
    val pop: Double,
    val rain: HourlyWeatherRain,
    val sys: HourlyWeatherSys,
    val visibility: Int,
    val weather: List<HourlyWeatherWeather>,
    val wind: HourlyWeatherWind
)

data class HourlyWeatherCity(
    val coord: HourlyWeatherCoord,
    val country: String,
    val id: Int,
    val name: String,
    val population: Int,
    val sunrise: Int,
    val sunset: Int,
    val timezone: Int
)

data class HourlyWeatherClouds(
    val all: Int
)

data class HourlyWeatherCoord(
    val lat: Double,
    val lon: Double
)

data class HourlyWeatherMain(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
)

data class HourlyWeatherRain(
    val `3h`: Double
)

data class HourlyWeatherSys(
    val pod: String
)

data class HourlyWeatherWeather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
)

data class HourlyWeatherWind(
    val deg: Int,
    val gust: Double,
    val speed: Double
)