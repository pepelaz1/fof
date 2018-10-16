package ru.pepelaz.fof.activities

import android.Manifest.permission.*
import android.app.Activity.RESULT_OK
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Environment.getExternalStoragePublicDirectory
import android.provider.MediaStore
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_main.*
import ru.pepelaz.fof.BuildConfig
import ru.pepelaz.fof.R
import ru.pepelaz.fof.activities.locations.LocationsActivity
import ru.pepelaz.fof.activities.species.SpeciesActivity
import ru.pepelaz.fof.activities.tides.TidesActivity
import ru.pepelaz.fof.activities.weather.WeatherActivity
import ru.pepelaz.fof.data.CoordinatesEvent
import ru.pepelaz.fof.flashlight.Flashlight
import ru.pepelaz.fof.flashlight.FlashlightFactory
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.helpers.PresentLocationCoords
import ru.pepelaz.fof.helpers.RxBus
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val PERMISSION_REQUEST = 1001
    private lateinit var flashlight: Flashlight
    private var isFlashlightEnabled = false
    val REQUEST_TAKE_PHOTO = 1
    var currentPhotoPath: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        flashlight = FlashlightFactory.newInstance(this, Build.VERSION_CODES.KITKAT)

        if ((ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(this, CAMERA) != PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE) != PERMISSION_GRANTED) ||
            (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE) != PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(ACCESS_FINE_LOCATION, CAMERA, READ_EXTERNAL_STORAGE,
                    WRITE_EXTERNAL_STORAGE), PERMISSION_REQUEST)
        } else {
            getCoordinates()
            flashlight.onStart()
        }

        imageViewFish.setOnTouchListener(touchListener)
        imageViewRecords.setOnTouchListener(touchListener)
        imageViewTackle.setOnTouchListener(touchListener)
        imageViewBalt.setOnTouchListener(touchListener)
        imageViewPlaces.setOnTouchListener(touchListener)
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
        imageViewTorch.setOnTouchListener(touchListener)
        imageViewCoarse.setOnTouchListener(touchListener)
        imageViewFacebook.setOnTouchListener(touchListener)

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

        imageViewPlaces.setOnClickListener({
            startActivity(Intent(this, PlacesActivity::class.java))
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
            openCameraIntent()
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

        imageViewTorch.setOnClickListener({
            isFlashlightEnabled = !isFlashlightEnabled
            flashlight.enable(isFlashlightEnabled)

            textViewTorch.text = (if (isFlashlightEnabled) "Torch on" else "Torch off" )
        })

        imageViewCoarse.setOnClickListener({
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("http://coarsefishingfacts.com"))
            startActivity(i)
        })

        imageViewFacebook.setOnClickListener({
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/SeaFishingFacts/"))
            startActivity(i)
        })
        Log.d("test_test","Destiny: " + resources.getDisplayMetrics().density)
    }


    var imageFilePath: String = ""
    @Throws(IOException::class)
    private fun createImageFile(): File {

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "IMG_$timeStamp"

        var dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Fof")
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return File.createTempFile(imageFileName, ".jpg", dir)
    }


    private fun openCameraIntent() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        var photoFile: File? = null
        try {
            photoFile = createImageFile()
        }
        catch (e: IOException) {
        }

        if (photoFile != null) {
            imageFilePath = photoFile.absolutePath
            val photoURI = FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", photoFile)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            startActivityForResult(intent, REQUEST_TAKE_PHOTO)
        }
    }

    private fun galleryAddPic() {

        val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
        val contentUri = Uri.fromFile(File(imageFilePath))
        intent.data = contentUri
        this.sendBroadcast(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_TAKE_PHOTO ->{
                if(resultCode == RESULT_OK){
                    galleryAddPic()
                }
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>,
                                            @NonNull grantResults: IntArray) {
        Log.d("test_test", "onRequestPermissionsResult grantResults: " + grantResults)
        when (requestCode) {
            PERMISSION_REQUEST -> {
                if (!grantResults.isEmpty())
                    if (grantResults[0] == PERMISSION_GRANTED) {
                        getCoordinates()
                    }
                    if (grantResults[1] == PERMISSION_GRANTED) {
                        flashlight.onPermissionGranted()
                        flashlight.onStart()
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
//    fun buttonGoSiteClicked(v: View) {
//        startActivity(Intent(this, SiteActivity::class.java))
//    }

}
