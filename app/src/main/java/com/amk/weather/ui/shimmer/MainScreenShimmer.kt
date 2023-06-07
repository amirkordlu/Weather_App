package com.amk.weather.ui.shimmer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amk.weather.R
import com.amk.weather.ui.mainscreen.MainToolbar
import com.amk.weather.ui.theme.CardViewBackground
import com.amk.weather.ui.theme.TemperatureItemBackground
import com.amk.weather.ui.theme.WeatherTheme
import com.amk.weather.util.MyScreens
import com.valentinilk.shimmer.shimmer
import dev.burnoo.cokoin.navigation.getNavController

@Composable
fun MainScreenShimmer() {
    val navigation = getNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            MainToolbar()

            CityNameShimmer()

            WeatherShimmer()

            Column(
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            ) {
                repeat(3) {
                    WeatherInfoShimmer()
                }
            }

            TempDaysShimmer {
                navigation.navigate(MyScreens.WeatherScreen.route)
            }

            Surface(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(start = 32.dp, top = 4.dp, bottom = 4.dp, end = 32.dp)
                    .shimmer(),
                shape = RoundedCornerShape(14.dp),
                color = CardViewBackground,
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
            ) {}

            LazyRow(
                modifier = Modifier.padding(start = 16.dp, top = 2.dp, bottom = 10.dp)
            ) {
                items(8) {
                    TemperatureShimmer()
                }
            }

        }
    }

}

@Composable
fun TemperatureShimmer() {
    Surface(
        modifier = Modifier
            .padding(start = 6.dp, end = 6.dp)
            .size(56.dp, 98.dp)
            .shimmer(),
        shape = RoundedCornerShape(48.dp),
        color = TemperatureItemBackground
    ) {

    }
}

@Composable
fun TempDaysShimmer(onDaysWeather: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 32.dp, top = 8.dp)
        ) {

            Surface(
                modifier = Modifier
                    .size(70.dp, 45.dp)
                    .padding(top = 8.dp)
                    .shimmer(),
                shape = RoundedCornerShape(14.dp),
                color = CardViewBackground,
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
            ) {}

            Surface(
                modifier = Modifier
                    .size(80.dp, 45.dp)
                    .padding(start = 8.dp, top = 8.dp)
                    .shimmer(),
                shape = RoundedCornerShape(14.dp),
                color = CardViewBackground,
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
            ) {}

        }


        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(top = 14.dp, end = 20.dp)
        ) {

            Surface(
                modifier = Modifier
                    .size(80.dp, 45.dp)
                    .padding(top = 8.dp)
                    .shimmer(),
                shape = RoundedCornerShape(14.dp),
                color = CardViewBackground,
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
            ) {}

            IconButton(onClick = { onDaysWeather.invoke() }) {
                Surface(
                    modifier = Modifier
                        .size(40.dp)
                        .padding(top = 5.dp)
                        .shimmer(),
                    shape = RoundedCornerShape(14.dp),
                    color = CardViewBackground,
                    border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
                ) {}
            }
        }
    }
}

@Composable
fun WeatherInfoShimmer() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(78.dp)
            .padding(top = 2.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
            .shimmer(),
        shape = RoundedCornerShape(14.dp),
        color = CardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
    ) {
    }

}

@Composable
fun WeatherShimmer() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(start = 8.dp, top = 4.dp, end = 8.dp)
            .shimmer()
    ) {
        Surface(
            modifier = Modifier
                .height(193.dp)
                .fillMaxWidth()
                .padding(24.dp)
                .shimmer(),
            shape = RoundedCornerShape(14.dp),
            color = CardViewBackground,
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
        ) {}
    }
}

@Composable
fun CityNameShimmer() {

    Surface(
        modifier = Modifier
            .size(250.dp, 65.dp)
            .padding(top = 24.dp, start = 32.dp)
            .shimmer(),
        shape = RoundedCornerShape(14.dp),
        color = CardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
    ) {}

    Surface(
        modifier = Modifier
            .size(150.dp, 45.dp)
            .padding(top = 8.dp, start = 32.dp)
            .shimmer(),
        shape = RoundedCornerShape(14.dp),
        color = CardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
    ) {}

}

@Preview(showBackground = true)
@Composable
fun MainScreenShimmerPreview() {
    WeatherTheme {
        MainScreenShimmer()
    }
}