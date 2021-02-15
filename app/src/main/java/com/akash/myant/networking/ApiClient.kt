package com.akash.myant.networking

import com.akash.myant.Constant
import com.akash.myant.data.CityWeather
import com.akash.myant.data.CityWeatherDetail
import com.akash.myant.data.Location
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import org.json.JSONObject

open class ApiClient {

    fun loadData(location: Location, callBack: OnCompletion<ArrayList<CityWeather>>){
        val url = "https://api.openweathermap.org/data/2.5/find?lat=${location.latitude}&lon=${location.longitude}&cnt=${Constant.NUMBER_OF_CITY}&units=metric&appid=${Constant.API_KEY}"
        AndroidNetworking.get(url)
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {

                    val resultArray = ArrayList<CityWeather>()

                    response?.let {
                        val jsonArray = it.getJSONArray("list")

                        for (i in 0 until jsonArray.length()) {
                            val json: JSONObject = jsonArray.getJSONObject(i)
                            val id = json.getInt("id")
                            val name = json.getString("name")

                            val mainBlock = json.getJSONObject("main")

                            val temp = mainBlock.getDouble("temp")
                            val humidity = mainBlock.getInt("humidity")

                            resultArray.add(CityWeather(id, name, temp, humidity))
                        }
                    }

                    callBack.onComplete(resultArray)
                }

                override fun onError(anError: ANError?) {

                }
            })
    }

    fun loadCityData(cityId: Int, callBack: OnCompletion<CityWeatherDetail>) {
        val url = "https://api.openweathermap.org/data/2.5/weather?id=$cityId&units=metric&appid=${Constant.API_KEY}"

        AndroidNetworking.get(url)
            .setPriority(Priority.IMMEDIATE)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    response?.let {

                        val name = response.getString("name")
                        val mainBlock = it.getJSONObject("main")
                        val temp = mainBlock.getDouble("temp")
                        val feel_like = mainBlock.getDouble("feels_like")
                        val pressure = mainBlock.getInt("pressure")

                        val windBlock = it.getJSONObject("wind")
                        val wind_speed = windBlock.getDouble("speed")
                        val wind_direction = windBlock.getInt("deg")

                        val min = mainBlock.getDouble("temp_min")
                        val max = mainBlock.getDouble("temp_max")

                        val data = CityWeatherDetail(name, temp, feel_like, pressure, wind_speed, min, max, wind_direction)

                        callBack.onComplete(data)
                    }
                }

                override fun onError(anError: ANError?) {

                }
            })
    }
}