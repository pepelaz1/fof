package ru.pepelaz.foffree.storages

import ru.pepelaz.foffree.models.Weather

object WeatherStorage {
    private var weather: ArrayList<Weather>? = null

    fun get(): ArrayList<Weather>? {
        return weather
    }

    fun set(weather: ArrayList<Weather>) {
        this.weather = weather
    }
}