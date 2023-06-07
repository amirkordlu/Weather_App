package com.amk.weather.ui.shimmer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amk.weather.ui.daysweather.WeatherToolbar
import com.amk.weather.ui.theme.BigCardViewBackground
import com.amk.weather.ui.theme.CardViewBackground
import com.amk.weather.ui.theme.WeatherTheme
import com.valentinilk.shimmer.shimmer
import dev.burnoo.cokoin.navigation.getNavController

@Composable
fun DaysWeatherShimmer() {
    val navigation = getNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            WeatherToolbar { navigation.popBackStack() }

            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(236.dp)
                    .padding(top = 12.dp, start = 32.dp, end = 32.dp, bottom = 22.dp)
                    .shimmer(),
                shape = RoundedCornerShape(24.dp),
                color = BigCardViewBackground,
                border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.70f))
            ) {}

            Column(
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            ) {
                repeat(5) {
                    WeekWeatherItem()
                }
            }
        }
    }
}

@Composable
fun WeekWeatherItem() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(88.dp)
            .padding(top = 2.dp, start = 32.dp, end = 32.dp, bottom = 12.dp)
            .shimmer(),
        shape = RoundedCornerShape(24.dp),
        color = CardViewBackground,
        border = BorderStroke(0.5.dp, Color.White.copy(alpha = 0.40f))
    ) {
    }
}

@Preview(showBackground = true)
@Composable
fun DaysWeatherShimmerPreview() {
    WeatherTheme {
        DaysWeatherShimmer()
    }
}