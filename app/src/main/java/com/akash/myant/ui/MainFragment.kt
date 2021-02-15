package com.akash.myant.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.akash.myant.Constant
import com.akash.myant.R
import com.akash.myant.data.CityWeather
import com.akash.myant.data.Event
import com.akash.myant.ui.adapter.OnItemClickListener
import com.akash.myant.ui.adapter.WeatherDataAdapter
import com.akash.myant.viewmodel.LocationViewModel
import kotlinx.android.synthetic.main.first_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import java.util.*

class MainFragment: Fragment() {

    lateinit var locationViewModel: LocationViewModel
    lateinit var adapter: WeatherDataAdapter

    private val itemClickListener: OnItemClickListener<CityWeather> = object : OnItemClickListener<CityWeather> {
        override fun onClick(t: CityWeather) {
            val bundle = Bundle()
            bundle.putInt(Constant.EXTRA_CITY_ID, t.id)
            findNavController().navigate(R.id.action_to_detail_fragment, bundle)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.first_fragment, container, false)
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().register(this)
    }

    override fun onPause() {
        super.onPause()
        EventBus.getDefault().unregister(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_view.layoutManager = LinearLayoutManager(context)

        adapter = WeatherDataAdapter(itemClickListener)
        recycler_view.adapter = adapter

        locationViewModel = ViewModelProvider.AndroidViewModelFactory
            .getInstance(requireActivity().application)
            .create(LocationViewModel::class.java)

        locationViewModel.weatherLiveData.observe(viewLifecycleOwner, Observer {
            adapter.updateData(it as ArrayList<CityWeather>)
        })

        getDataFromDB()
    }

    private fun getDataFromDB() {
        Toast.makeText(requireActivity(), "Get Data from DB", Toast.LENGTH_SHORT).show()
        locationViewModel.getDataFromDb()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            Constant.PERMISSION_REQUEST_CODE -> {

            }
        }
    }

    @Subscribe
    fun onMessageEvent(event: Event){
        getDataFromDB()
    }
}