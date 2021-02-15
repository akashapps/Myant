package com.akash.myant.service

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.app.JobIntentService
import androidx.lifecycle.ViewModelProvider
import com.akash.myant.Constant
import com.akash.myant.MainActivity
import com.akash.myant.data.CityWeather
import com.akash.myant.data.Event
import com.akash.myant.data.Location
import com.akash.myant.networking.OnCompletion
import com.akash.myant.networking.Repository
import com.akash.myant.viewmodel.LocationViewModel
import org.greenrobot.eventbus.EventBus
import java.util.*
import kotlin.collections.ArrayList

class WeatherDataService: JobIntentService() {

    companion object {
        fun enqueueWork(activity: MainActivity){
            enqueueWork(activity, WeatherDataService::class.java, 1000, Intent())
        }
    }

    lateinit var repo : Repository

    var t = Timer()
    val handler: Handler = Handler(Looper.myLooper()!!)
    private var doAsynchronousTask: TimerTask = object : TimerTask() {
        override fun run() {
            handler.post(Runnable {
                Toast.makeText(applicationContext, "Refresh from API", Toast.LENGTH_SHORT).show()
                repo.getCurrentLocationData(object : OnCompletion<Location> {
                    override fun onComplete(t: Location?) {
                        t?.let {
                            repo.getWeatherData(it, object : OnCompletion<ArrayList<CityWeather>> {
                                override fun onComplete(t: ArrayList<CityWeather>?) {
                                    EventBus.getDefault().post(Event())
                                }
                            })
                        }
                    }
                })
            })
        }
    }

    override fun onCreate() {
        super.onCreate()
        repo = Repository(applicationContext)
    }

    override fun onHandleWork(intent: Intent) {
        t.schedule(doAsynchronousTask, 0, Constant.INTERVAL)
    }
}