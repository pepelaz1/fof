package ru.pepelaz.foffree.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.models.Weather
import ru.pepelaz.foffree.storages.WeatherStorage
import java.text.SimpleDateFormat

class WeatherAdapter : ArrayAdapter<Weather> {
    internal var context: Context

    constructor(context: Context) : super(context, R.layout.weather_row, WeatherStorage.get()!!) {
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
        var str = SimpleDateFormat("MMM dd EEEE").format(date)

        str = str.substring(0, 1).toUpperCase() + str.substring(1)
        (view!!.findViewById<View>(R.id.textViewDate) as TextView).text  = str

        (view!!.findViewById<View>(R.id.textViewMaxtempC) as TextView).text = weather.maxtempC.toString()+ " \u2103"
        (view!!.findViewById<View>(R.id.textViewMintempC) as TextView).text = weather.mintempC.toString()+ " \u2103"

        (view!!.findViewById<View>(R.id.textViewRain) as TextView).text = weather.hourly!![0].precipMM.toString()
        (view!!.findViewById<View>(R.id.textViewDesciption) as TextView).text = weather.hourly!![0].weatherDesc

        Glide.with(context).load(weather.hourly!![0].weatherIconUrl)
                .into( (view!!.findViewById<View>(R.id.imageView) as ImageView))

        val wind = weather.hourly!![0].windspeedMiles.toString() + " mph " + weather.hourly!![0].winddir16Point
        (view!!.findViewById<View>(R.id.textViewWind) as TextView).text = wind

        if (position % 2 != 0)
           view.setBackgroundColor(Color.parseColor("#c7c7c7"));
        else
            view.setBackgroundColor(Color.parseColor("#ffffff"));

        return view!!
    }
}