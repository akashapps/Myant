package com.akash.myant.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akash.myant.data.CityWeather

@Dao
interface CityWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCity(list: ArrayList<CityWeather>)

    @Query("SELECT * FROM CityWeather")
    fun getAllCity() : LiveData<List<CityWeather>>
}