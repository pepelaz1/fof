package ru.pepelaz.fof.activities.weather

import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weather.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.R.id.*
import ru.pepelaz.fof.activities.SpeciesActivity
import ru.pepelaz.fof.adapters.WeatherAdapter
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.network.Communicator
import ru.pepelaz.fof.storages.WeatherStorage

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        loadWeather()
    }

    fun constructLocationName(a: Address): String {
        var result = ""
        if (a.thoroughfare != null) {
            result += a.thoroughfare
        }
        if (a.subThoroughfare != null) {
            result += ", " + a.subThoroughfare
        }

        if (a.locality != null) {
            result += ", " + a.locality
        }

        if (a.countryName != null) {
            result += ", " + a.countryName
        }
        return result;
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

        // CurrentCoords.latitude = 36.539296
         //CurrentCoords.longitude = -4.6226728

        //CurrentCoords.latitude = 53.57
        //CurrentCoords.longitude = -2.94

        //CurrentCoords.latitude = -1.0
        //CurrentCoords.longitude = -1.0



        val geocoder = Geocoder(this)
        var addresses = geocoder.getFromLocation(
                CurrentCoords.latitude,
                CurrentCoords.longitude,
                1)
        if (addresses != null && addresses.size > 0) {
            textView2.text = constructLocationName(addresses[0])
        }



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
