package ru.pepelaz.fof.activities.locations

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import io.nlopez.smartlocation.SmartLocation
import kotlinx.android.synthetic.main.activity_locations.*
import ru.pepelaz.fof.R

class LocationEditActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_location)

    }


}
