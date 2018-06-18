package ru.pepelaz.fof

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {
        lateinit var instance: App

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    init {
        instance = this
    }
}