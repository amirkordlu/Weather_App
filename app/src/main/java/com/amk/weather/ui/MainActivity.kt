package com.amk.weather.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.amk.weather.di.myModules
import com.amk.weather.ui.daysweather.DaysWeather
import com.amk.weather.ui.mainscreen.MainWeatherScreen
import com.amk.weather.ui.theme.WeatherTheme
import com.amk.weather.util.MyScreens
import dev.burnoo.cokoin.Koin
import dev.burnoo.cokoin.navigation.KoinNavHost
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Koin(appDeclaration = {
                androidContext(this@MainActivity)
                modules(myModules)
            }) {
                WeatherTheme {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colors.background
                    ) {
                        WeatherUI()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherTheme {
        WeatherUI()
    }
}

@Composable
fun WeatherUI() {
    val navController = rememberNavController()
    KoinNavHost(
        navController = navController,
        startDestination = MyScreens.MainScreen.route
    ) {

        composable(MyScreens.MainScreen.route) {
            MainWeatherScreen()

        }

        composable(
            route = MyScreens.WeatherScreen.route
        ) {
            DaysWeather()
        }

    }
}