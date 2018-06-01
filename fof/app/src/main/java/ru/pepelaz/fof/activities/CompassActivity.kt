package ru.pepelaz.fof.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.pepelaz.fof.R
import android.widget.TextView
import android.hardware.SensorManager
import android.widget.ImageView
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import kotlinx.android.synthetic.main.activity_compass.*
import ru.pepelaz.fof.R.id.imageViewCompass




class CompassActivity : AppCompatActivity(), SensorEventListener {


    private var currentDegree = 0f
    private val sensorManager: SensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass)

    }

    override fun onResume() {
        super.onResume()
        if (sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION),
                SensorManager.SENSOR_DELAY_NORMAL) == false) {
            textViewError.visibility = View.VISIBLE
            imageViewCompass.visibility = View.GONE
        } else {
            textViewError.visibility = View.GONE
            imageViewCompass.visibility = View.VISIBLE
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this);
    }

    override fun onSensorChanged(event: SensorEvent?) {
        // get the angle around the z-axis rotated
        val degree = Math.round(event!!.values[0])

        textViewHeading.setText("Heading: " + degree.toString() + " degrees")

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
        imageViewCompass.startAnimation(ra)
        currentDegree = -degree.toFloat()
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
