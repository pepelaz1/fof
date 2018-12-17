package ru.pepelaz.fof.activities.locations

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_saved_locations.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.adapters.LocationsAdapter
import ru.pepelaz.fof.database.Location
import ru.pepelaz.fof.database.LocationDao

class LocationsSavedActivity : AppCompatActivity() {

    var items = ArrayList<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_locations)

        reload()
    }

    fun reload() {
        val locations = LocationDao().queryForAll()
        items = ArrayList()
        for (location in locations)
          items.add(location)





        val adapter = LocationsAdapter(this, items)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            val location = items[position]

            val intent = Intent(this, LocationEditActivity::class.java)
            val b = Bundle()
            b.putInt("locationId", location.Id!!)
            b.putDouble("latitude", location.Latitude!!)
            b.putDouble("longitude", location.Longitude!!)
            intent.putExtras(b)
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        reload()
    }

    fun onHomeClick(v: View) {
        finish()
    }

}