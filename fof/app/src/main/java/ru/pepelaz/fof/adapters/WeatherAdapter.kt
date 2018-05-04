package ru.pepelaz.fof.adapters

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.pepelaz.fof.R
import ru.pepelaz.fof.models.Weather
import ru.pepelaz.fof.storages.WeatherStorage
import java.text.DateFormat
import java.text.SimpleDateFormat

class WeatherAdapter : ArrayAdapter<Weather> {
    internal var context: Context

    constructor(context: Context) : super(context, R.layout.weather_row, WeatherStorage.get()) {
        this.context = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (convertView == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            view = inflater.inflate(R.layout.weather_row, parent, false)
        }

        val weather = WeatherStorage.get()!![position]
        val date = SimpleDateFormat("yyyy-MM-dd").parse(weather.date)
        var str = SimpleDateFormat("MMM dd").format(date)

        str = str.substring(0, 1).toUpperCase() + str.substring(1)
        (view!!.findViewById<View>(R.id.textViewDate) as TextView).text  = str

        (view!!.findViewById<View>(R.id.textViewMaxtempC) as TextView).text = weather.maxtempC.toString()+ " \u2103"
        (view!!.findViewById<View>(R.id.textViewMintempC) as TextView).text = weather.mintempC.toString()+ " \u2103"

        (view!!.findViewById<View>(R.id.textViewRain) as TextView).text = weather.hourly!![0].precipMM.toString()
        (view!!.findViewById<View>(R.id.textViewDesciption) as TextView).text = weather.hourly!![0].weatherDesc

        Glide.with(context).load(weather.hourly!![0].weatherIconUrl)
                .into( (view!!.findViewById<View>(R.id.imageView) as ImageView))


        http@ //cdn.worldweatheronline.net/images/wsymbols01_png_64/wsymbol_0002_sunny_intervals.png
        (view!!.findViewById<View>(R.id.textViewWind) as TextView).text = "10 kmph SSE"

        if (position % 2 != 0)
           view.setBackgroundColor(Color.parseColor("#c7c7c7"));
        else
            view.setBackgroundColor(Color.parseColor("#ffffff"));

//        val category = categories[position]
//
//        (view!!.findViewById<View>(R.id.checkbox) as CheckBox).isChecked = category.Selected
//        (view!!.findViewById<View>(R.id.textview) as TextView).text = category.Name
//
//
//        (view!!.findViewById<View>(R.id.imageview) as ImageView).setImageResource(
//                context.getResources().getIdentifier(prefix + category.Id.toString(), "drawable", context.getPackageName()))


        return view!!
    }
}