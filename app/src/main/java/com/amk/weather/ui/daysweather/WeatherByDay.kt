package com.amk.weather.ui.daysweather

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.amk.weather.R
import com.amk.weather.di.myModules
import com.amk.weather.model.data.DaysWeather
import com.amk.weather.model.data.DaysWeatherResponse
import com.amk.weather.ui.shimmer.DaysWeatherShimmer
import com.amk.weather.ui.theme.*
import com.amk.weather.util.*
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

class WeatherByDay : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Koin(appDeclaration = { modules(myModules) }) {
                WeatherTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        DaysWeather()
                    }
                }
            }
        }
    }
}

@Composable
fun DaysWeather() {
    val navigation = getNavController()
    val viewModel = getNavViewModel<WeatherByDayViewModel>()
    val navBackStackEntry = navigation.currentBackStackEntry
    val lat = navBackStackEntry?.arguments?.getString(KEY_WEATHER_LAT)
    val lon = navBackStackEntry?.arguments?.getString(KEY_WEATHER_LON)
    viewModel.getWeatherByDay(lat!!.toDouble(), lon!!.toDouble())

    Image(
        painter = painterResource(R.drawable.img_background),
        contentDescription = "Background",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxSize()
    )

    if (!viewModel.showLoading.value) {
        Box(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {

                WeatherToolbar() {
                    navigation.popBackStack()
                }

                TomorrowWeather(viewModel.weatherByDay.value)

                WeekWeather(viewModel.weatherByDay.value)

            }
        }
    } else {
        DaysWeatherShimmer()
    }


}

@Composable
fun WeatherToolbar(onBackClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 18.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(onClick = { onBackClicked.invoke() }) {
            Image(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
            )
        }

        Text(
            text = "Next Days", fontSize = 20.sp, fontFamily = interRegular
        )

        IconButton(onClick = { /*TODO*/ }) {

        }
    }
}

@Composable
fun TomorrowWeather(weather: DaysWeatherResponse) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(236.dp)
            .padding(top = 12.dp, start = 32.dp, end = 32.dp, bottom = 22.dp),
        shape = RoundedCornerShape(24.dp),
        color = BigCardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.70f))
    ) {

        Column {

            if (weather.list.isNotEmpty()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp, top = 20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {

                    Row(
                        modifier = Modifier,
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = "Tomorrow",
                            fontFamily = interSemiBold,
                            color = Color(48, 51, 69),
                            fontSize = 14.sp
                        )

                        IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(66.dp)) {

                        }

                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Text(
                            text = convertKelvinToCelsius(weather.list[1].temp.day).toString() + " °",
                            fontSize = 14.sp,
                            fontFamily = interBold,
                            color = Color(48, 51, 69)
                        )

                        Image(
                            modifier = Modifier
                                .size(66.dp),
                            painter = painterResource(id = weatherIcon(weather.list[1].weather[0].icon)),
                            contentDescription = null
                        )

                    }

                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, top = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    TomorrowWeatherItem(
                        R.drawable.ic_umberella,
                        weather.list[1].rain.toInt().toString() + " mm"
                    )
                    TomorrowWeatherItem(
                        R.drawable.ic_wind,
                        convertMeterOnMinToKilometerOnHour(weather.list[1].speed).toString() + " km/h"
                    )
                    TomorrowWeatherItem(
                        R.drawable.ic_humidity,
                        weather.list[1].humidity.toString() + " %"
                    )

                }
            }
        }
    }
}

@Composable
fun TomorrowWeatherItem(itemImage: Int, itemValue: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Image(
            modifier = Modifier.size(44.dp),
            painter = painterResource(id = itemImage),
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 10.dp),
            text = itemValue,
            fontFamily = interSemiBold,
            fontSize = 14.sp,
            color = Color(48, 51, 69)
        )
    }
}

@Composable
fun WeekWeather(weather: DaysWeatherResponse) {
    LazyColumn(
        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        userScrollEnabled = true
    ) {
        items(weather.list.size) {
            if (it in 2..6) {
                WeekWeatherItem(weather.list[it], weatherIcon(weather.list[it].weather[0].icon))
            }
        }
    }
}

@Composable
fun WeekWeatherItem(daysWeather: DaysWeather, weatherIcon: Int) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(top = 2.dp, start = 16.dp, end = 16.dp, bottom = 12.dp),
        shape = RoundedCornerShape(24.dp),
        color = CardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp, top = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = convertUnixToDate(daysWeather.dt.toLong()),
                    fontFamily = interSemiBold,
                    color = Color(48, 51, 69),
                    fontSize = 14.sp
                )

                IconButton(onClick = { /*TODO*/ }, modifier = Modifier.size(64.dp)) {

                }

            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = convertKelvinToCelsius(daysWeather.temp.day).toString() + " °",
                    fontSize = 14.sp,
                    fontFamily = interBold,
                    color = Color(48, 51, 69)
                )

                Image(
                    modifier = Modifier
                        .size(64.dp),
                    painter = painterResource(id = weatherIcon),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeatherByDayPreview() {
    WeatherTheme {
        DaysWeather()
    }
}