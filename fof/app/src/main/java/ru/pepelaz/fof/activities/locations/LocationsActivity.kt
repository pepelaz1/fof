package ru.pepelaz.fof.activities.locations

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_locations.*
import ru.pepelaz.fof.R

class LocationsActivity : AppCompatActivity() {

    var longitude: Double = 0.toDouble()
    var latitude: Double = 0.toDouble()
    val LOCATION_PERMISSION_ID = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        if (ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION), LOCATION_PERMISSION_ID)
        } else {
            getCoordinates()
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        Log.d("test_test", "onRequestPermissionsResult grantResults: " + grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_ID -> {
                if (!grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getCoordinates()
                }
            }
        }
    }

    fun getCoordinates() {
        SmartLocation.with(this).location()
                .start { location ->
                    Log.d("test_test", "Lat: " + location.latitude + ", Long: " + location.longitude)
                    longitude = location.longitude
                    latitude = location.latitude

                    textViewLatitudeValue.text = latitude.toString()
                    textViewLongitudeValue.text = longitude.toString()
                }

    }

    fun onRecordClick(v: View) {
        finish()
    }

    fun onSavedClick(v: View) {
        finish()
    }

 }
