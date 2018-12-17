package ru.pepelaz.fof.activities.compass

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_saved_locations.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.adapters.LocationsAdapter
import ru.pepelaz.fof.database.Location
import ru.pepelaz.fof.database.LocationDao

class CompassLocationsActivity : AppCompatActivity() {

    var items = ArrayList<Location>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compass_locations)

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
            var data = Intent()
            data.putExtra("LocationId", location.Id)
            setResult(Activity.RESULT_OK, data);
            finish()
        }
    }


    fun onBackClick(v: View) {
        setResult(Activity.RESULT_CANCELED, null);
        finish()
    }

}