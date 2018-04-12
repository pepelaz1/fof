package ru.pepelaz.fof.activities.locations

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
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

import com.google.android.gms.maps.model.Marker




class LocationsActivity : FragmentActivity(), OnMapReadyCallback {

    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()
    val LOCATION_PERMISSION_ID = 1001

    private var map: GoogleMap? = null
    private var marker: Marker? = null

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

                    val editor = getSharedPreferences("fof", Context.MODE_PRIVATE).edit()
                    editor.putString("latitude", latitude.toString())
                    editor.putString("longitude", longitude.toString())
                    editor.apply()
                }
    }

    fun onRecordClick(v: View) {
        val intent = Intent(this, LocationEditActivity::class.java)
        val b = Bundle()
        b.putDouble("latitude", latitude)
        b.putDouble("longitude", longitude)
        intent.putExtras(b)
        startActivity(intent)
    }

    fun onSavedClick(v: View) {
        startActivity(Intent(this, LocationsSavedActivity::class.java))
    }

    override fun onMapReady(googleMap: GoogleMap) {
       // Log.d("test_test","on map ready")
        map = googleMap

        map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
        map!!.uiSettings.setZoomControlsEnabled(true)
        map!!.uiSettings.setCompassEnabled(true)
        map!!.uiSettings.setMyLocationButtonEnabled(true)
        map!!.uiSettings.setAllGesturesEnabled(true)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map!!.setMyLocationEnabled(true);

        map!!.setOnMapClickListener {
//            Log.d("test_test","on map click")
//            if (marker != null)
//                marker!!.remove()
//
//            marker = map!!.addMarker(MarkerOptions().position(it).title("Me"))
//            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 13.0f))
        }
    }

    private fun updateMapPosition() {
        if (map != null)  {
            val current = LatLng(latitude, longitude)
            marker = map!!.addMarker(MarkerOptions().position(current).title("Me"))
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 13.0f))
        }
    }

 }
