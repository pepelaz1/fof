package ru.pepelaz.fof

import android.app.Application
import android.content.Context
import android.support.multidex.MultiDexApplication
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger

class App : MultiDexApplication() {

    companion object {
        lateinit var instance: MultiDexApplication

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this

    }

    override fun onCreate() {
        super.onCreate()
        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);
    }
}