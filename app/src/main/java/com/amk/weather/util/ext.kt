package com.amk.weather.util

import android.annotation.SuppressLint
import android.util.Log
import com.amk.weather.R
import com.amk.weather.model.data.*
import kotlinx.coroutines.CoroutineExceptionHandler
import java.text.SimpleDateFormat
import java.util.*

val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
    Log.e("Error", "error -> ${throwable.message}")
}

val CWREx = CurrentWeatherResponse(
    "", Clouds(0), 0, Coord(0.0, 0.0), 0, 0,
    Main(0.0, 0, 0, 0, 0, 300.0, 0.0, 0.0), "",
    Sys("", 0, 0, 0, 0), 0, 0, listOf(Weather("", "", 0, "")), Wind(0, 0.0, 0.0)
)

val DWREx = DaysWeatherResponse(City(FutureCoord(0.0, 0.0), "", 0, "", 0, 0), 0, 0, listOf(), 0.0)

val HWREx = HourlyWeatherResponse(
    HourlyWeatherCity(HourlyWeatherCoord(0.0, 0.0), "", 0, "", 0, 0, 0, 0),
    0,
    0,
    listOf(
        HourlyWeather(
            HourlyWeatherClouds(0),
            0,
            "",
            HourlyWeatherMain(0.0, 0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0),
            0.0,
            HourlyWeatherRain(0.0),
            HourlyWeatherSys(""),
            0,
            listOf(HourlyWeatherWeather("", "", 0, "")),
            HourlyWeatherWind(0, 0.0, 0.0)
        )
    ),
    0
)

fun convertKelvinToCelsius(kelvin: Double): Int {
    val celsius = kelvin - 273.15
    return celsius.toInt()
}

fun convertMeterOnMinToKilometerOnHour(wind: Double): Int {
    return (wind * 3.6).toInt()
}

@SuppressLint("SimpleDateFormat")
fun getDateOfMobile(): String {
    val weekAndMonth = SimpleDateFormat("EEE, MMM d")
    return weekAndMonth.format(Date())
}

@SuppressLint("SimpleDateFormat")
fun convertUnixToDate(unixTime: Long): String {
    val date = Date(unixTime * 1000)
    val dateFormat = SimpleDateFormat("EEEE")
    return dateFormat.format(date)
}

@SuppressLint("SimpleDateFormat")
fun getDayOfWeek(): String {
    val calendar = Calendar.getInstance()
    val sdf = SimpleDateFormat("EEEE")
    return sdf.format(calendar.time)
}

fun formatTimeString(dateTimeString: String): String {
    return if (dateTimeString.isNotBlank()) {
        val dateTimeFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val dateTime = dateTimeFormat.parse(dateTimeString)
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        timeFormat.format(dateTime)
    } else {
        ""
    }
}

fun weatherIcon(icon: String): Int {
    when (icon) {
        "01d" -> return (R.drawable.ic_sun)
        "02d" -> return (R.drawable.ic_sunny_cloudy)
        "03d" -> return (R.drawable.ic_cloudy)
        "04d" -> return (R.drawable.ic_cloudy)
        "09d" -> return (R.drawable.ic_cloudy_rainy)
        "10d" -> return (R.drawable.ic_rainy)
        "11d" -> return (R.drawable.ic_thunder)
        "13d" -> return (R.drawable.ic_snowy)
        "50d" -> return (R.drawable.ic_windy)
        "01n" -> return (R.drawable.ic_moon)
        "02n" -> return (R.drawable.ic_sunny_cloudy)
        "03n" -> return (R.drawable.ic_cloudy)
        "04n" -> return (R.drawable.ic_cloudy)
        "09n" -> return (R.drawable.ic_cloudy_rainy)
        "10n" -> return (R.drawable.ic_rainy)
        "11n" -> return (R.drawable.ic_thunder)
        "13n" -> return (R.drawable.ic_snowy)
        "50n" -> return (R.drawable.ic_windy)
    }
    return R.drawable.ic_error
}


