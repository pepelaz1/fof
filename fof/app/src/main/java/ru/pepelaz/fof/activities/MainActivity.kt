package ru.pepelaz.fof.activities

import android.Manifest
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_locations.view.*
import kotlinx.android.synthetic.main.activity_main.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.activities.locations.LocationsActivity
import ru.pepelaz.fof.activities.tides.TidesActivity
import ru.pepelaz.fof.activities.weather.WeatherActivity
import ru.pepelaz.fof.data.CoordinatesEvent
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.helpers.PresentLocationCoords
import ru.pepelaz.fof.helpers.RxBus


class MainActivity : AppCompatActivity() {

    val LOCATION_PERMISSION_ID = 1001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_ID)
        } else {
            getCoordinates()
        }

        imageViewFish.setOnTouchListener(touchListener)
        imageViewRecords.setOnTouchListener(touchListener)
        imageViewTackle.setOnTouchListener(touchListener)
        imageViewBalt.setOnTouchListener(touchListener)
        imageViewDistribution.setOnTouchListener(touchListener)
        imageViewSeasons.setOnTouchListener(touchListener)
        imageViewLocations.setOnTouchListener(touchListener)
        imageViewGlossary.setOnTouchListener(touchListener)
        imageViewCamera.setOnTouchListener(touchListener)
        imageViewCooking.setOnTouchListener(touchListener)
        imageViewTides.setOnTouchListener(touchListener)
        imageViewWeather.setOnTouchListener(touchListener)
        imageViewKnot.setOnTouchListener(touchListener)
        imageViewAbout.setOnTouchListener(touchListener)
        imageViewCompass.setOnTouchListener(touchListener)
        imageViewQuiz.setOnTouchListener(touchListener)

        imageViewFish.setOnClickListener({
            startActivity(Intent(this, SpeciesActivity::class.java))
        })

        imageViewRecords.setOnClickListener({
            startActivity(Intent(this, RecordsActivity::class.java))
        })

        imageViewTackle.setOnClickListener({
            startActivity(Intent(this, TackleActivity::class.java))
        })

        imageViewBalt.setOnClickListener({
            startActivity(Intent(this, BaitActivity::class.java))
        })

        imageViewDistribution.setOnClickListener({
            startActivity(Intent(this, DistributionActivity::class.java))
        })

        imageViewSeasons.setOnClickListener({
            startActivity(Intent(this, SeasonsActivity::class.java))
        })

        imageViewLocations.setOnClickListener({
            startActivity(Intent(this, LocationsActivity::class.java))
        })

        imageViewGlossary.setOnClickListener({
            startActivity(Intent(this, GlossaryActivity::class.java))
        })

        imageViewCamera.setOnClickListener({
          //  startActivity(Intent(this, CameraActivity::class.java))
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if(cameraIntent.resolveActivity(packageManager)!=null){
                startActivityForResult(cameraIntent,1)
            }
        })

        imageViewCooking.setOnClickListener({
            startActivity(Intent(this, CookingActivity::class.java))
        })

        imageViewTides.setOnClickListener({
            startActivity(Intent(this, TidesActivity::class.java))
        })

        imageViewWeather.setOnClickListener({
            startActivity(Intent(this, WeatherActivity::class.java))
        })

        imageViewKnot.setOnClickListener({
            startActivity(Intent(this, KnotActivity::class.java))
        })

        imageViewAbout.setOnClickListener({
            startActivity(Intent(this, AboutActivity::class.java))
        })

        imageViewCompass.setOnClickListener({
            startActivity(Intent(this, CompassActivity::class.java))
        })

        imageViewQuiz.setOnClickListener({
            startActivity(Intent(this, QuizActivity::class.java))
        })
        Log.d("test_test","Destiny: " + resources.getDisplayMetrics().density)
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
                   // CurrentCoords.latitude = location.latitude
                   // CurrentCoords.longitude = location.longitude

                    PresentLocationCoords.latitude = location.latitude
                    PresentLocationCoords.longitude = location.longitude

                    if (CurrentCoords.latitude == 0.0 || CurrentCoords.longitude == 0.0) {
                        CurrentCoords.longitude = PresentLocationCoords.longitude
                        CurrentCoords.latitude = PresentLocationCoords.latitude
                    }

                    RxBus.publish(CoordinatesEvent(location.latitude, location.longitude))
                }
    }

    object touchListener: View.OnTouchListener {
        override fun onTouch(v: View, m: MotionEvent): Boolean {
            // Perform tasks here
            when(m.action) {
                MotionEvent.ACTION_DOWN -> (v as ImageView).alpha = 0.5.toFloat()
                MotionEvent.ACTION_UP -> (v as ImageView).alpha = 1.0.toFloat()
            }
            return false
        }
    }

    fun buttonQuitClicked(v: View) {
        finish()
    }
    fun buttonGoSiteClicked(v: View) {
        startActivity(Intent(this, SiteActivity::class.java))
    }



}
