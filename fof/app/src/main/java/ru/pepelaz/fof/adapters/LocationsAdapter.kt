package ru.pepelaz.fof.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.pepelaz.fof.R
import ru.pepelaz.fof.database.Location

class LocationsAdapter(context: Context, locations: ArrayList<Location>) :
        ArrayAdapter<Location>(context,  android.R.layout.simple_list_item_1, locations){

    var locations: ArrayList<Location>

    init {
        this.locations = locations
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var vi = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var v = vi.inflate(android.R.layout.simple_list_item_1,null)
        var textView =  v.findViewById(android.R.id.text1) as TextView
        textView.text = locations[position].Name
        return v
  }

}