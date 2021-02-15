package com.akash.myant.ui.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akash.myant.R
import com.akash.myant.data.CityWeather

class ListItemViewHolder(val view: View): RecyclerView.ViewHolder(view) {

    private val nameTextView: TextView = view.findViewById(R.id.name_text_view)
    private val tempTextView: TextView = view.findViewById(R.id.temp_text_view)
    private var humidityTextView: TextView = view.findViewById(R.id.humidity_text_view)

    fun setData(cityWeather: CityWeather){
        nameTextView.text = cityWeather.cityName
        tempTextView.text = view.context.getString(R.string.temp , cityWeather.temperature.toString())
        humidityTextView.text = view.context.getString(R.string.humidity, cityWeather.humidity.toString())
    }

}