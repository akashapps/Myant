package com.akash.myant.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akash.myant.R
import com.akash.myant.data.CityWeather

class WeatherDataAdapter(val itemClickAction: OnItemClickListener<CityWeather>) : RecyclerView.Adapter<ListItemViewHolder>() {

    private var data = ArrayList<CityWeather>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_city, parent, false)
        return ListItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListItemViewHolder, position: Int) {
        val cityWeather = data[position]
        holder.setData(cityWeather)

        holder.view.setOnClickListener {
            itemClickAction.onClick(cityWeather)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun updateData(it: ArrayList<CityWeather>) {
        data.clear()
        data = it
        notifyDataSetChanged()
    }
}