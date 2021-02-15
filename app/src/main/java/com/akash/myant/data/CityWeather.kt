package com.akash.myant.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CityWeather")
data class CityWeather(
    @PrimaryKey()
    var id: Int = 0,
    var cityName: String,
    var temperature: Double,
    var humidity: Int
)