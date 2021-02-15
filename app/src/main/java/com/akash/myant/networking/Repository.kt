package com.akash.myant.networking

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.akash.myant.MyApplication
import com.akash.myant.data.CityWeather
import com.akash.myant.data.Location
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class Repository(val context: Context) {

    private val apiClient = ApiClient()

    private val fuseLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getCurrentLocationData(onCompletion: OnCompletion<Location>) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        fuseLocationClient.lastLocation.addOnSuccessListener { data ->
            data?.let {
                onCompletion.onComplete(Location(it.latitude, it.longitude))
            }
        }
    }

    fun getWeatherData(location: Location, callBack: OnCompletion<ArrayList<CityWeather>>){
        apiClient.loadData(location, object : OnCompletion<ArrayList<CityWeather>>{
            override fun onComplete(t: ArrayList<CityWeather>?) {
                if (t != null){
                    MyApplication.db?.let {
                        Thread{
                            it.getCityWeatherDao().insertCity(t)
                        }.start()
                    }

                    callBack.onComplete(t)
                }
            }
        })
    }

}