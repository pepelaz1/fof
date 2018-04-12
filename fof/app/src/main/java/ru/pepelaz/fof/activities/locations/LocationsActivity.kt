package ru.pepelaz.fof.activities.locations

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_locations.*
import ru.pepelaz.fof.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng

class LocationsActivity : FragmentActivity(), OnMapReadyCallback {

    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()
    val LOCATION_PERMISSION_ID = 1001

    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

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

                    updateMapPosition()
                }

    }

    fun onRecordClick(v: View) {
        startActivity(Intent(this, LocationEditActivity::class.java))
    }

    fun onSavedClick(v: View) {
        startActivity(Intent(this, LocationsSavedActivity::class.java))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        Log.d("test_test","on map ready")
        map = googleMap
    }

    private fun updateMapPosition() {
        if (map != null)
        {
            val current = LatLng(latitude, longitude)
            map!!.addMarker(MarkerOptions().position(current).title("Present location"))
            map!!.moveCamera(CameraUpdateFactory.newLatLng(current))
        }
    }

 }
