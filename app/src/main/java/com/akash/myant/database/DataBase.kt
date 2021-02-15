package com.akash.myant.database

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room
import com.akash.myant.data.CityWeather

@Database(entities = [CityWeather::class], version = 1, exportSchema = false)
abstract class DataBase: RoomDatabase() {

    abstract fun getCityWeatherDao() : CityWeatherDao

    companion object{
        fun create(context: Context): DataBase {
            return Room.databaseBuilder(context, DataBase::class.java, "database.db")
                .build()
        }
    }
}
