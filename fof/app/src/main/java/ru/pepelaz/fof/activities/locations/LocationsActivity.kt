package ru.pepelaz.fof.activities.locations

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.activity_locations.*
import ru.pepelaz.fof.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng

import com.google.android.gms.maps.model.Marker
import ru.pepelaz.fof.data.CoordinatesEvent
import ru.pepelaz.fof.fragments.PresentLocationFragment
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.helpers.RxBus


class LocationsActivity : FragmentActivity(), OnMapReadyCallback {

    private var longitude: Double = 0.toDouble()
    private var latitude: Double = 0.toDouble()

    private var map: GoogleMap? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            setContentView(R.layout.activity_locations)
            val mapFragment = supportFragmentManager
                    .findFragmentById(R.id.map) as SupportMapFragment
            mapFragment.getMapAsync(this)


            RxBus.listen(CoordinatesEvent::class.java).subscribe({

                longitude = it.longitude
                latitude = it.latitude


                updateMapPosition()

            })
        }
        catch (ex: Exception) {
            Log.d("test_test", ex.toString())
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

        latitude = CurrentCoords.latitude
        longitude = CurrentCoords.longitude
        updateMapPosition()
    }



    private fun updateMapPosition() {
        textViewLatitudeValue.text = latitude.toString()
        textViewLongitudeValue.text = longitude.toString()

        if (map != null)  {
            val current = LatLng(latitude, longitude)
            marker = map!!.addMarker(MarkerOptions().position(current).title("Me"))
            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 13.0f))
        }
        (presentLocationFragment as PresentLocationFragment).onNewCoords()
    }

 }
