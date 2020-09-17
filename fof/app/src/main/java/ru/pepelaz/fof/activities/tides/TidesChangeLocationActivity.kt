package ru.pepelaz.fof.activities.tides

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_edit_location.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.database.Location
import ru.pepelaz.fof.database.LocationDao
import ru.pepelaz.fof.helpers.CurrentCoords


class TidesChangeLocationActivity :  FragmentActivity(), OnMapReadyCallback {

    private var locationId = 0
    private var latitude: Double = 0.toDouble()
    private var longitude: Double = 0.toDouble()

    //private var curLatitude: Double = 0.toDouble()
    //private var curLongitude: Double = 0.toDouble()

    private var map: GoogleMap? = null
    private var marker: Marker? = null

    private val locationDao = LocationDao()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tides_new_location)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    constraint1.setOnClickListener({
        hideKeyboard()
    })
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
            hideKeyboard()

            latitude = Math.round(it.latitude * 10000000.0) / 10000000.0
            longitude = Math.round(it.longitude * 10000000.0) / 10000000.0


        }

        latitude = CurrentCoords.latitude
        longitude = CurrentCoords.longitude
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

    private fun hideKeyboard() {
        val view = getCurrentFocus()
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    fun onHomeClick(v: View) {
        setResult(1)
        finish();
    }


    fun onBackClick(v: View) {
        finish()
    }


    fun onFindClick(v: View) {
        CurrentCoords.longitude = longitude
        CurrentCoords.latitude = latitude
        setResult(2)
        finish()

    }

}
