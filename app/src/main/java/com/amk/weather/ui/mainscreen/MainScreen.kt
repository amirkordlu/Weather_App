package com.amk.weather.ui.mainscreen

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amk.weather.R
import com.amk.weather.di.myModules
import com.amk.weather.model.data.CurrentWeatherResponse
import com.amk.weather.model.data.HourlyWeather
import com.amk.weather.model.data.HourlyWeatherResponse
import com.amk.weather.ui.shimmer.MainScreenShimmer
import com.amk.weather.ui.theme.*
import com.amk.weather.util.*
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.getNavController
import dev.burnoo.cokoin.navigation.getNavViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Koin(appDeclaration = { modules(myModules) }) {
                WeatherTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        MainWeatherScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun MainWeatherScreen() {
    val context = LocalContext.current
    val navigation = getNavController()
    val viewModel = getNavViewModel<MainScreenViewModel>()
    val hourlyViewModel = getNavViewModel<HourlyWeatherViewModel>()

    val selectedLocation = remember { mutableStateOf<LatLng?>(null) }
    val showMap = remember { mutableStateOf(true) }

    if (showMap.value) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            onMapClick = { loc ->
                selectedLocation.value = loc
                viewModel.getWeatherInfo(loc.latitude, loc.longitude)
                hourlyViewModel.getHourlyWeather(loc.latitude, loc.longitude)
                showMap.value = false
            }
        )
    } else {


        Image(
            painter = painterResource(R.drawable.img_background),
            contentDescription = "Background",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
        )

        if (!NetworkChecker(context).isInternetConnected) {
            NoInternetDialog(onTryAgain = {
                if (NetworkChecker(context).isInternetConnected) {
                    viewModel.getWeatherInfo(
                        selectedLocation.value!!.latitude,
                        selectedLocation.value!!.longitude
                    )
                } else {
                    Toast.makeText(context, "No internet connection!", Toast.LENGTH_SHORT).show()
                }
            }, onDismiss = {})
        }

        if (viewModel.showLoading.value) {

            MainScreenShimmer()

        } else {

            Box(modifier = Modifier.fillMaxSize()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {

                    MainToolbar()

                    CityName(viewModel.weatherInfo.value, getDateOfMobile())

                    Weather(viewModel.weatherInfo.value)

                    WeatherInfo(viewModel.weatherInfo.value)

                    TempDays {
                        navigation.navigate("${MyScreens.WeatherScreen.route}/${selectedLocation.value!!.latitude}/${selectedLocation.value!!.longitude}")
                    }

                    Divider(
                        color = Color(226, 162, 114),
                        thickness = 0.5.dp,
                        modifier = Modifier.padding(
                            start = 32.dp,
                            top = 8.dp,
                            bottom = 8.dp,
                            end = 32.dp
                        )
                    )

                    Temperature(hourlyViewModel.hourlyWeather.value)

                }

            }
        }
    }
}

@Composable
fun MainToolbar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 16.dp)
                    .size(40.dp)
            )
        }

        Text(text = "Weather App", fontSize = 22.sp, fontFamily = interMedium)

        IconButton(onClick = { /*TODO*/ }) {
            Image(
                painter = painterResource(id = R.drawable.ic_menu),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
            )
        }
    }

}

@Composable
fun CityName(weatherName: CurrentWeatherResponse, date: String) {
    Column(
        modifier = Modifier
            .padding(top = 20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.Start
    )
    {

        Text(
            modifier = Modifier.padding(top = 8.dp, start = 32.dp),
            text = weatherName.name,
            fontFamily = interMedium,
            fontSize = 34.sp,
            color = Color(49, 51, 65)
        )

        Text(
            text = date,
            modifier = Modifier.padding(top = 4.dp, start = 32.dp),
            color = Color(154, 147, 140),
            fontFamily = interRegular
        )

    }
}

@Composable
fun Weather(weather: CurrentWeatherResponse) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 6.dp, end = 8.dp)
    ) {

        Image(
            modifier = Modifier
                .size(193.dp, 190.dp),
            painter = painterResource(id = weatherIcon(weather.weather[0].icon)),
            contentDescription = null
        )

        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = convertKelvinToCelsius(weather.main.temp).toString(),
                fontFamily = interBold,
                fontSize = 80.sp,
                color = Color(48, 51, 69)
            )

            Text(
                modifier = Modifier.padding(bottom = 18.dp),
                text = weather.weather[0].main,
                fontFamily = interRegular,
                fontSize = 30.sp,
                color = Color(48, 51, 69)
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.Top)
                .padding(top = 22.dp),
            text = "° C",
            fontFamily = interLight,
            fontSize = 18.sp,
            color = Color(48, 51, 69)
        )
    }
}

@Composable
fun WeatherInfo(weatherInfo: CurrentWeatherResponse) {
    WeatherInfoItem(R.drawable.ic_pressure, "Pressure", "${weatherInfo.main.pressure} Pa")
    WeatherInfoItem(
        R.drawable.ic_wind,
        "Wind",
        "${convertMeterOnMinToKilometerOnHour(weatherInfo.wind.speed)} km/h"
    )
    WeatherInfoItem(R.drawable.ic_humidity, "Humidity", "${weatherInfo.main.humidity} %")
}

@Composable
fun WeatherInfoItem(itemImage: Int, itemText: String, itemValue: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(76.dp)
            .padding(start = 32.dp, end = 32.dp, bottom = 8.dp),
        shape = RoundedCornerShape(24.dp),
        color = CardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.70f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(46.dp),
                painter = painterResource(id = itemImage),
                contentDescription = null
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = itemText,
                fontFamily = interRegular,
                color = Color(48, 51, 69),
                fontSize = 14.sp
            )

        }


        Row(
            modifier = Modifier
                .padding(end = 38.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {

            Text(
                text = itemValue,
                fontSize = 14.sp,
                fontFamily = interRegular,
                color = Color(48, 51, 69),
            )

        }

    }
}

@Composable
fun Temperature(hourlyWeather: HourlyWeatherResponse) {
    LazyRow(
        modifier = Modifier.padding(top = 8.dp, bottom = 14.dp),
        contentPadding = PaddingValues(start = 16.dp, end = 16.dp),
        userScrollEnabled = true
    ) {
        items(hourlyWeather.list.size) {
            if (it in 0..7) {
                TemperatureItem(
                    hourlyWeather.list[it]
                )
            }
        }
    }
}

@Composable
fun TemperatureItem(hourlyWeather: HourlyWeather) {
    Surface(
        modifier = Modifier
            .padding(start = 6.dp, end = 6.dp)
            .size(56.dp, 98.dp),
        shape = RoundedCornerShape(48.dp),
        color = TemperatureItemBackground
    ) {

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = formatTimeString(hourlyWeather.dt_txt),
                fontSize = 14.sp,
                fontFamily = interRegular,
                color =
                TemperatureItemTime,
            )

            Image(
                modifier = Modifier.size(34.dp),
                painter = painterResource(weatherIcon(hourlyWeather.weather[0].icon)),
                contentDescription = null
            )

            Text(
                text = convertKelvinToCelsius(hourlyWeather.main.temp).toString() + " °",
                fontSize = 14.sp,
                fontFamily = interBold,
                color = Color(48, 51, 69),
            )

        }

    }

}

@Composable
fun TempDays(onDaysWeather: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 32.dp, top = 14.dp)
        ) {

            Text(
                text = "Today",
                fontSize = 14.sp,
                fontFamily = interBold,
                color = Color(49, 51, 65),
            )

            Text(
                modifier = Modifier.padding(start = 24.dp),
                text = "Tomorrow",
                fontSize = 14.sp,
                fontFamily = interRegular,
                color = Color(214, 153, 107),
            )

        }


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 14.dp, end = 20.dp)
        ) {

            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = "Next 7 Days",
                fontSize = 14.sp,
                fontFamily = interRegular,
                color = Color(214, 153, 107),
            )

            IconButton(onClick = { onDaysWeather.invoke() }) {
                Image(
                    painter = painterResource(id = R.drawable.ic_next),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                )
            }

        }


    }
}

@Composable
fun NoInternetDialog(
    onTryAgain: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss.invoke() },
        title = { Text(text = "No Internet Connection") },
        text = { Text(text = "Please check your internet connection and try again.") },
        confirmButton = {
            TextButton(onClick = { onTryAgain.invoke() }) {
                Text(text = "Try again")
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        MainWeatherScreen()
    }
}