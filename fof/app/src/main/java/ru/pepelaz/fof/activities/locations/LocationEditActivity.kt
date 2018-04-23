package ru.pepelaz.fof.activities.locations

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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


class LocationEditActivity :  FragmentActivity(), OnMapReadyCallback {

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
        setContentView(R.layout.activity_edit_location)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        latitude = intent.extras.getDouble("latitude")
        longitude = intent.extras.getDouble("longitude")

//        val sp = getSharedPreferences("fof", Context.MODE_PRIVATE)
//        curLatitude = (sp.getString("latitude","0.0")).toDouble()
//        curLongitude  = (sp.getString("longitude","0.0")).toDouble()

        locationId = intent.extras.getInt("locationId")
        if (locationId != 0) {
            val location = locationDao.getById(locationId)
            editTextName.setText(location.Name)
            editTextNote.setText(location.Notes)
            textView1.setText("Saved location selected")
        }

        textViewLatitudeValue.text = latitude.toString()
        textViewLongitudeValue.text = longitude.toString()

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

            textViewLatitudeValue.text = latitude.toString()
            textViewLongitudeValue.text = longitude.toString()
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
        textViewLatitudeValue.text = latitude.toString()
        textViewLongitudeValue.text = longitude.toString()
    }

    private fun hideKeyboard() {
        val view = getCurrentFocus()
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    fun onSaveClick(v: View) {

        if (editTextName.text.toString() == "") {
            Toast.makeText(this, "Name must not be empty", Toast.LENGTH_SHORT).show()
            return
        }
        try {
            if (locationId == 0) {
                locationDao.add(Location(null, editTextName.text.toString(), editTextNote.text.toString(),
                        latitude, longitude))

            } else {
                val location = locationDao.getById(locationId)
                location.Name = editTextName.text.toString()
                location.Notes = editTextNote.text.toString()
                location.Latitude = latitude
                location.Longitude = longitude
                locationDao.update(location)
            }
            Toast.makeText(this, "Location successfully saved", Toast.LENGTH_SHORT).show()
            editTextName.setText("")
            editTextNote.setText("")
            finish()

        } catch (ex: Exception) {
            Toast.makeText(this, "Failed to save location, error: " + ex.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun onDeleteClick(v: View) {
        try {

            if (locationId != 0) {
                val location = locationDao.getById(locationId)
                AlertDialog.Builder(this)
                        .setMessage("Are you sure to delete location: " + location.Name)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton("Yes", { _, _ ->
                            locationDao.delete(location)
                            Toast.makeText(this, "Location successfully deleted", Toast.LENGTH_SHORT).show()
                            finish()
                        })
                        .setNegativeButton("No", null).show()
            }

        } catch (ex: Exception) {
            Toast.makeText(this, "Failed to delete location, error: " + ex.message, Toast.LENGTH_SHORT).show()
        }
    }


    fun onCancelClick(v: View) {
        finish()
    }

    fun onResetClick(v: View) {
        if (marker != null)
            marker!!.remove()

        longitude = CurrentCoords.longitude
        latitude = CurrentCoords.latitude
        updateMapPosition()
    }

}
