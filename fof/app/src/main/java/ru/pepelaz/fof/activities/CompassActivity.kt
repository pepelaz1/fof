package ru.pepelaz.fof.activities

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.pepelaz.fof.R
import android.hardware.SensorManager
import android.support.v4.app.ActivityCompat
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.activity_compass.*

import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import ru.pepelaz.fof.dialogs.WarningDialog
import ru.pepelaz.fof.fragments.PresentLocationFragment
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.helpers.PresentLocationCoords
import android.util.DisplayMetrics
import android.widget.Button


class CompassActivity : AppCompatActivity(), SensorEventListener {


    private var currentDegree = 0f
    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)

        compass.setOnClickListener({
            scrollViewError.visibility = View.VISIBLE
            layoutCompass.visibility = View.GONE
        })

        var prefs = getSharedPreferences("fof",0)
        if (prefs.getBoolean("showWarningDialog", true) == true)
            showWarningDialog()
    }

    fun showWarningDialog() {
        var warningDialog = WarningDialog(this)
        warningDialog.show()
        val yes = warningDialog.findViewById(R.id.yes) as Button
        yes.setOnClickListener({
            warningDialog.dismiss()
        })

        val no = warningDialog.findViewById(R.id.no) as Button
        no.setOnClickListener({
            finish()
        })


   }


    override fun onResume() {
        super.onResume()
        if (sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL) == false) {
            scrollViewError.visibility = View.VISIBLE
            layoutCompass.visibility = View.GONE
        } else {
            scrollViewError.visibility = View.GONE
            layoutCompass.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // get the angle around the z-axis rotated
        val degree = Math.round(event!!.values[0])

        headingDegree.text = degree.toString() + 0x00B0.toChar()
        headingTitle.text = evaluateTitleFromDegree(degree)

        bearingDegree.text = headingDegree.text
        bearingTitle.text = headingTitle.text

        // create a rotation animation (reverse turn degree degrees)
        val ra = RotateAnimation(
                currentDegree,
                -degree.toFloat(),
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f)

        // how long the animation will take place
        ra.duration = 210

        // set the animation after the end of the reservation status
        ra.fillAfter = true

        // Start the animation
        compass.startAnimation(ra)
        currentDegree = -degree.toFloat()
    }

    fun evaluateTitleFromDegree(degree: Int) : String {

        var result = ""
        if (degree >= 0 && degree < 45) {
            result = "North"
        } else if (degree >= 45 && degree < 90) {
            result = "North-East"
        } else if (degree >= 90 && degree < 135) {
            result = "East"
        } else if (degree >= 135 && degree < 180) {
            result = "South-East"
        } else if (degree >= 180 && degree < 225) {
            result = "South"
        } else if (degree >= 225 && degree < 270) {
            result = "South-West"
        } else if (degree >= 270 && degree < 315) {
            result = "West"
        } else if (degree >= 315 && degree < 360) {
            result = "North-West"
        }
        return result
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }


    fun onHomeClick(v: View) {
        finish()
    }

    fun onNewTargetClick(v: View) {
        //CurrentCoords.longitude = PresentLocationCoords.longitude
        //CurrentCoords.latitude = PresentLocationCoords.latitude

        //updateMapPosition()
    }

    fun onWarningClick(v: View) {
        showWarningDialog()
    }
}

