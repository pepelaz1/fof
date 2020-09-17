package ru.pepelaz.fof.activities.compass

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.pepelaz.fof.R
import android.hardware.SensorManager
import android.util.Log
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.activity_compass.*

import ru.pepelaz.fof.dialogs.WarningDialog
import android.widget.Button
import ru.pepelaz.fof.activities.locations.LocationsSavedActivity
import ru.pepelaz.fof.data.CoordinatesEvent
import ru.pepelaz.fof.database.LocationDao
import ru.pepelaz.fof.helpers.PresentLocationCoords
import ru.pepelaz.fof.helpers.RxBus


class CompassActivity : AppCompatActivity(), SensorEventListener {

    private val COMPASS_LOCATIONS_REQUEST = 100

    private var currentDegree = 0f

    private var presentLat = 0.0
    private var presentLong = 0.0

    private var locationLat = 0.0
    private var locationLong = 0.0

    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    @SuppressLint("CheckResult")
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

        RxBus.listen(CoordinatesEvent::class.java).subscribe({
            Log.d("test_test","coordinates received on compass")

            presentLat = it.latitude
            presentLong = it.longitude

            updatePresentCoords()

            calculate()
        })

        presentLat = PresentLocationCoords.latitude
        presentLong = PresentLocationCoords.longitude

        updatePresentCoords()

        val locationId = prefs.getInt("CompassLastLocationId", 0)
        if (locationId != 0)
            processNewLocation(locationId)

        calculate()
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

        if (bearingDegree.text == "") {
            bearingDegree.text = headingDegree.text
            bearingTitle.text = headingTitle.text
        }

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
        startActivityForResult(Intent(this, CompassLocationsActivity::class.java), COMPASS_LOCATIONS_REQUEST)
    }

    fun onWarningClick(v: View) {
        showWarningDialog()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == COMPASS_LOCATIONS_REQUEST) {
            var locationId = data!!.getIntExtra("LocationId", 0)
            if (locationId > 0) {
                processNewLocation(locationId)

                val editor = getSharedPreferences("fof",0).edit()
                editor.putInt("CompassLastLocationId", locationId)
                editor.apply()

                calculate()
            }
        }
    }

    fun processNewLocation(locationId: Int) {
        val location = LocationDao().getById(locationId)

        locationLat = location.Latitude!!
        locationLong = location.Longitude!!

        locationName.text = location.Name
        latLocationVal.text = locationLat.toString()
        lngLocationVal.text = locationLong.toString()
    }

    fun updatePresentCoords() {
        latPresentVal.text = presentLat.toString()
        lngPresentVal.text = presentLong.toString()

        if (latLocationVal.text == "" && lngLocationVal.text == "" ) {
            latLocationVal.text = latPresentVal.text
            lngLocationVal.text = lngPresentVal.text
        }
    }

    fun calculate() {

//        presentLat = 51.752
//        presentLong = -1.2577
//        locationLat = 52.2053
//        locationLong = 0.0218

        var degree = calcBearing()

        bearingDegree.text = degree.toString() + 0x00B0.toChar()
        bearingTitle.text = evaluateTitleFromDegree(degree)

        distance.text = calcDistance().toString()
    }

    fun calcBearing() : Int {

        //ATAN2(COS(nowLatRadians)*SIN(goToLatRadians)-SIN(nowLatRadians)*COS(goToLatRadians)*COS(goToLongRadians-nowLongRadians),SIN(goToLongRadians-nowLongRadians)*COS(goToLatRadians))
        //ATAN2(COS(nowLatRadians)*SIN(goToLatRadians)-SIN(nowLatRadians)*COS(goToLatRadians)*COS(goToLongRadians-nowLongRadians);SIN(goToLongRadians-nowLongRadians)*COS(goToLatRadians))

        var plat = Math.toRadians(presentLat)
        var plong = Math.toRadians(presentLong)
        var llat = Math.toRadians(locationLat)
        var llong = Math.toRadians(locationLong)

        var radians = Math.atan2(Math.sin(llong - plong) * Math.cos(llat), Math.cos(plat) * Math.sin(llat) - Math.sin(plat) * Math.cos(llat) * Math.cos(llong - plong))

        var degrees = Math.round(Math.toDegrees(radians)).toInt()

        var result = degrees
        if (result < 0)
            result = 360 + result

        return result
    }

    fun calcDistance() : Int {

        //ROUND(ACOS(SIN(nowLatRadians)*SIN(goToLatRadians)+COS(nowLatRadians)*COS(goToLatRadians)*COS(goToLongRadians-nowLongRadians))*3981.875,0)

        var plat = Math.toRadians(presentLat)
        var plong = Math.toRadians(presentLong)
        var llat = Math.toRadians(locationLat)
        var llong = Math.toRadians(locationLong)

        return Math.round(Math.acos(Math.sin(plat) * Math.sin(llat) + Math.cos(plat) * Math.cos(llat) * Math.cos(llong - plong)) *  3981.875).toInt()
    }
}

