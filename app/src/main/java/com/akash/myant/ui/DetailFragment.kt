package com.akash.myant.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akash.myant.Constant
import com.akash.myant.R
import com.akash.myant.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.detail_fragment.*

class DetailFragment: Fragment() {

    lateinit var locationViewModel: LocationViewModel
    var cityID: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.getInt(Constant.EXTRA_CITY_ID)?.let {
            cityID = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        locationViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(LocationViewModel::class.java)

        locationViewModel.weatherDetailData.observe(viewLifecycleOwner, {
            title.text = it.name
            temp.text = it.temp.toString()
            feels_like.text = it.feelsLike.toString()
            min_temp.text = it.temp_min.toString()
            max_temp.text = it.temp_max.toString()
            pressure.text = it.pressure.toString()
            wind_speed.text = it.windSpeed.toString()
            wind_direction.text = it.windDirection.toString()
        })


        loadDetailWeatherData()
    }

    private fun loadDetailWeatherData() {
        locationViewModel.loadCityDetails(cityID)
    }
}