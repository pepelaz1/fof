package ru.pepelaz.foffree.activities.compass

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_saved_locations.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.adapters.LocationsAdapter
import ru.pepelaz.foffree.database.Location
import ru.pepelaz.foffree.database.LocationDao

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