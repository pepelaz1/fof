package ru.pepelaz.fof.activities.weather

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weather.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.R.id.*
import ru.pepelaz.fof.activities.SpeciesActivity
import ru.pepelaz.fof.adapters.WeatherAdapter
import ru.pepelaz.fof.data.CoordinatesEvent
import ru.pepelaz.fof.fragments.PresentLocationFragment
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.helpers.RxBus
import ru.pepelaz.fof.helpers.Utils
import ru.pepelaz.fof.network.Communicator
import ru.pepelaz.fof.storages.WeatherStorage

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        loadWeather()
    }


    fun onHomeClick(v: View) {
        finish()
    }

    fun onSelectNewLocationClick(v: View) {
        startActivityForResult(Intent(this, WeatherChangeLocationActivity::class.java), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 1)
            finish()
        else if (requestCode == 1 && resultCode == 2)
            loadWeather()
    }

    fun loadWeather() {

        (presentLocationFragment as PresentLocationFragment).onNewCoords()
        textViewLatitudeValue.text = CurrentCoords.latitude.toString()
        textViewLongitudeValue.text = CurrentCoords.longitude.toString()

        val coords = CurrentCoords.latitude.toString() + "," + CurrentCoords.longitude.toString()

        Communicator.service(this)!!.getWeather("b6add83687f743fb94a150227171510", coords, "yes", 24)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {data ->
                            Log.d("test_test", "data: " + data.weather!![0].date)
                            WeatherStorage.set(data.weather!!)
                            updateWeatherList()
                        },
                        {error ->
                            Log.d("test_test","Failed to get weather data, error: " + error.toString())
                            textViewError.visibility = View.VISIBLE
                            listViewWeather.visibility = View.GONE
                        }
                )

    }

    fun updateWeatherList() {
        listViewWeather!!.setAdapter(WeatherAdapter(this))
    }
}
