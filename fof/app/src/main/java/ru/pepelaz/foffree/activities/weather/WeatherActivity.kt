package ru.pepelaz.foffree.activities.weather

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weather.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.adapters.WeatherAdapter
import ru.pepelaz.foffree.fragments.PresentLocationFragment
import ru.pepelaz.foffree.helpers.CurrentCoords
import ru.pepelaz.foffree.network.Communicator
import ru.pepelaz.foffree.storages.WeatherStorage

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

    @SuppressLint("CheckResult")
    fun loadWeather() {

        (presentLocationFragment as PresentLocationFragment).onNewCoords()
        textViewLatitudeValue.text = CurrentCoords.latitude.toString()
        textViewLongitudeValue.text = CurrentCoords.longitude.toString()

       // CurrentCoords.latitude = 50.8094
       // CurrentCoords.longitude = -0.5506
        val coords = CurrentCoords.latitude.toString() + "," + CurrentCoords.longitude.toString()


       // Toast.makeText(this, coords, Toast.LENGTH_LONG).show()

        Communicator.service(this)!!.getWeather("b6add83687f743fb94a150227171510", coords, "yes", 24)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe (
                        {data ->
                             Log.d("test_test", "data: " + data)
                            Log.d("test_test", "data: " + data.weather!![0].date)
                            WeatherStorage.set(data.weather!!)
                            updateWeatherList()
                        },
                        {error ->
                            Log.d("test_test","Failed to get weather data, error: " + error.toString())
                            textViewError.visibility = View.VISIBLE
                            listViewWeather.visibility = View.GONE
                            Toast.makeText(this, error.toString(), Toast.LENGTH_LONG).show()
                        }
                )

    }

    fun updateWeatherList() {
        listViewWeather!!.setAdapter(WeatherAdapter(this))
    }
}
