package ru.pepelaz.fof.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.species_cell.view.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.storages.SpeciesStorage
import android.graphics.drawable.Drawable
import ru.pepelaz.fof.App
import java.io.InputStream


class SpeciesAdapter : BaseAdapter {
    var context: Context


    val specieImages = HashMap<String, Drawable>()

    constructor(context: Context) {
        this.context = context
    }

    override fun getCount(): Int {
        return SpeciesStorage.getSize()
    }

    override fun getItem(p0: Int): Any {
        return Any()
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.species_cell, parent, false)
        }

        val specie = SpeciesStorage.getAt(position)

        if (!specieImages.containsKey(specie.name)) {
            val path = "species/" + specie.name.toLowerCase() + "/" + specie.name.toLowerCase() + "_htm_files"
            val list = App.applicationContext().assets.list(path)
            for (fileName in list!!) {
                if (fileName.length == 6 && fileName.substringAfter(".").toLowerCase() == "jpg") {
                    // val imageFile = "species/"+ specie.name.toLowerCase() +"/" + specie.name.toLowerCase() + "_htm_files/10.jpg"
                    //val imageFile = "file:///android_asset/species/"+ specie.name.toLowerCase() +"/" + specie.name.toLowerCase() + ".htm"
                    val stream: InputStream = App.applicationContext().assets.open(path + "/" + fileName)
                    //view!!.imageView.setImageDrawable(Drawable.createFromStream(stream, null))
                    specieImages.put(specie.name, Drawable.createFromStream(stream, null))
                    break
                }
            }
        }
        view!!.imageView.setImageDrawable(specieImages.get(specie.name))
        view!!.textView.text = specie.name

        return view!!
    }
}