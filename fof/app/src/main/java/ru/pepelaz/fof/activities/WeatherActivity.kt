package ru.pepelaz.fof.activities

import android.location.Address
import android.location.Geocoder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_weather.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.helpers.CurrentCoords

class WeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

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


    fun buttonCloseClick(v: View) {
        finish()
    }
}
