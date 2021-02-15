package com.akash.myant.viewmodel

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.akash.myant.MyApplication
import com.akash.myant.data.CityWeather
import com.akash.myant.data.CityWeatherDetail
import com.akash.myant.data.Location
import com.akash.myant.networking.ApiClient
import com.akash.myant.networking.OnCompletion
import com.akash.myant.networking.Repository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationViewModel(application: Application) : AndroidViewModel(application) {

    private val apiClient = ApiClient()
    private val context: Context = application.applicationContext

    private val _weatherLiveData = MutableLiveData<List<CityWeather>>()
    var weatherLiveData: LiveData<List<CityWeather>> = _weatherLiveData

    private val _weatherDetailData = MutableLiveData<CityWeatherDetail>()
    var weatherDetailData: LiveData<CityWeatherDetail> = _weatherDetailData

    private val repository = Repository(context)

    private var location: Location? = null

    fun getDataFromDb(){
        MyApplication.db?.let { db ->
            val value = db.getCityWeatherDao().getAllCity().value

            if (value == null || value.isEmpty()){
                getCurrentLocationData()
            }else{
                _weatherLiveData.value = value
            }
        }
    }

    fun getCurrentLocationData() {

        repository.getCurrentLocationData(object : OnCompletion<Location> {
            override fun onComplete(t: Location?) {
                location = t
                getWeatherData()
            }
        })
    }

    fun getWeatherData(){

        location?.let { loc ->
            repository.getWeatherData(loc, object : OnCompletion<ArrayList<CityWeather>>{
                override fun onComplete(t: ArrayList<CityWeather>?) {
                    t?.let { data ->
                        _weatherLiveData.value = data
                    }
                }
            })
        }
    }

    fun loadCityDetails(cityId: Int) {
        apiClient.loadCityData(cityId, object : OnCompletion<CityWeatherDetail> {
            override fun onComplete(t: CityWeatherDetail?) {
                t?.let {
                    _weatherDetailData.value = it
                }
            }
        })
    }
}