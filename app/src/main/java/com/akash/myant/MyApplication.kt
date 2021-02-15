package com.akash.myant

import android.app.Application
import com.akash.myant.database.DataBase
import com.androidnetworking.AndroidNetworking
import org.greenrobot.eventbus.EventBus

class MyApplication : Application() {

    companion object {
        var db: DataBase? = null
    }

    override fun onCreate() {
        super.onCreate()

        AndroidNetworking.initialize(applicationContext)
        db = DataBase.create(applicationContext)
    }
}