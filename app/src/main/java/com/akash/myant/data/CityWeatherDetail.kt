package com.akash.myant.data

data class CityWeatherDetail(
    val name: String,
    val temp: Double,
    val feelsLike: Double,
    val pressure: Int,
    val windSpeed: Double,
    val temp_min: Double,
    val temp_max: Double,
    val windDirection: Int
)