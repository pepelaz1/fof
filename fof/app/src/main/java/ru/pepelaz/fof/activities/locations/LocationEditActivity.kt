package ru.pepelaz.fof.activities.locations

import android.Manifest
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_locations.*
import ru.pepelaz.fof.R

class LocationEditActivity :  FragmentActivity(), OnMapReadyCallback {

    private var locationId = 0
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    private var map: GoogleMap? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        latitude = intent.extras.getDouble("latitude")
        longitude = intent.extras.getDouble("longitude")
        locationId = intent.extras.getInt("locationId")

        textViewLatitudeValue.text = latitude.toString()
        textViewLongitudeValue.text = longitude.toString()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        // Log.d("test_test","on map ready")
        map = googleMap

        map!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
        map!!.uiSettings.setZoomControlsEnabled(true)
        map!!.uiSettings.setCompassEnabled(true)
        map!!.uiSettings.setMyLocationButtonEnabled(true)
        map!!.uiSettings.setAllGesturesEnabled(true)

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        map!!.setMyLocationEnabled(true);
        map!!.setOnMapClickListener {
           if (marker != null)
                marker!!.remove()

            marker = map!!.addMarker(MarkerOptions().position(it).title("Me"))
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(it, 13.0f))
        }


        updateMapPosition()
    }

    private fun updateMapPosition() {
        if (map != null)
        {
            val position = LatLng(latitude, longitude)
            marker = map!!.addMarker(MarkerOptions().position(position).title("Me"))
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 13.0f))

        }
    }


}
