package ru.pepelaz.fof.activities.tides

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.view.View
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_weather.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.R.id.*
import ru.pepelaz.fof.activities.SpeciesActivity
import ru.pepelaz.fof.adapters.WeatherAdapter
import ru.pepelaz.fof.helpers.CurrentCoords
import ru.pepelaz.fof.helpers.Utils
import ru.pepelaz.fof.network.Communicator
import ru.pepelaz.fof.storages.WeatherStorage
import java.util.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import kotlinx.android.synthetic.main.activity_tides.*
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class TidesActivity() : AppCompatActivity(), TidesCalendarFragment.ITidesCalendarFragment {

    override fun onDateSelected(date: Date) {
        Log.d("test_test", "date selected: " + date)
        rebuildChart()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tides)

        loadTides()
        rebuildChart()
    }

    fun onHomeClick(v: View) {
        finish()
    }

    fun onSelectNewLocationClick(v: View) {
        startActivityForResult(Intent(this, TidesChangeLocationActivity::class.java), 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == 1)
            finish()
        else if (requestCode == 1 && resultCode == 2)
            loadTides()
    }

    fun loadTides() {

        // CurrentCoords.latitude = 36.539296
         //CurrentCoords.longitude = -4.6226728

        //CurrentCoords.latitude = 53.57
        //CurrentCoords.longitude = -2.94

        //CurrentCoords.latitude = -1.0
        //CurrentCoords.longitude = -1.0



        val geocoder = Geocoder(this)
        var addresses = geocoder.getFromLocation(
                CurrentCoords.latitude,
                CurrentCoords.longitude,
                1)
        if (addresses != null && addresses.size > 0) {
            textView3.text = Utils.constructLocationName(addresses[0])
        }



       // textViewLatitudeValue.text = CurrentCoords.latitude.toString()
       // textViewLongitudeValue.text = CurrentCoords.longitude.toString()

        val coords = CurrentCoords.latitude.toString() + "," + CurrentCoords.longitude.toString()


//        Communicator.service(this)!!.getWeather("b6add83687f743fb94a150227171510", coords, "yes", 24)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe (
//                        {data ->
//                            Log.d("test_test", "data: " + data.weather!![0].date)
//                            WeatherStorage.set(data.weather!!)
//                            updateWeatherList()
//                        },
//                        {error ->
//                            Log.d("test_test","Failed to get weather data, error: " + error.toString())
//                            textViewError.visibility = View.VISIBLE
//                            listViewWeather.visibility = View.GONE
//                        }
//                )

    }


    fun rebuildChart() {
        if (chartView != null) {
            val entries = ArrayList<Entry>()
            val r = Random()
            for (i in 0..10) {
                entries.add(Entry(i.toFloat(), (r.nextInt(10 - 5) + 5).toFloat()))
            }

            val dataSet = LineDataSet(entries, "Label") // add entries to dataset
            dataSet.setColor(Color.BLUE)
            dataSet.setValueTextColor(Color.BLACK)
            dataSet.fillColor = Color.RED
            dataSet.setDrawFilled(true)
            dataSet.setDrawValues(false)

            dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

            val lineChart: LineChart = chartView

            val lineData = LineData(dataSet);
            lineChart.setData(lineData);
            lineChart.xAxis.position = XAxis.XAxisPosition.BOTTOM;
            lineChart.xAxis.valueFormatter = object: IAxisValueFormatter {
                private val format = SimpleDateFormat("HH:mm")

                override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                     var millis = TimeUnit.HOURS.toMillis(value.toLong())
                     return format.format( Date(millis))
                }

            }
            lineChart.axisRight.isEnabled = false
            lineChart.invalidate()
        }
    }


}
