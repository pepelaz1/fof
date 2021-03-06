package ru.pepelaz.foffree.activities.tides

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.helpers.CurrentCoords
import ru.pepelaz.foffree.network.Communicator
import ru.pepelaz.foffree.storages.WeatherStorage
import java.util.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.android.synthetic.main.activity_tides.*
import ru.pepelaz.foffree.fragments.CalendarFragment
import ru.pepelaz.foffree.fragments.PresentLocationFragment
import java.text.SimpleDateFormat
import java.util.concurrent.TimeUnit


class TidesActivity() : AppCompatActivity(), CalendarFragment.ICalendarFragment {

    var selectedDate: Date? = null
    var lineChart: LineChart? = null

    override fun onDateSelected(date: Date) {
        //Log.d("test_test", "date selected: " + SimpleDateFormat("yyyy-MM-dd").format(date))
        selectedDate = date
        loadTides()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_tides)

        setupChart()
        loadTides()

//        RxBus.listen(CoordinatesEvent::class.java).subscribe({
//
//            CurrentCoords.longitude = it.longitude
//            CurrentCoords.latitude = it.latitude
//
//            loadTides()
//
//        })
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
        (presentLocationFragment as PresentLocationFragment).onNewCoords()

        if (lineChart != null) {
            val coords = CurrentCoords.latitude.toString() + "," + CurrentCoords.longitude.toString()

            Communicator.service(this)!!.getWeather("b6add83687f743fb94a150227171510", coords, "yes", 24)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { data ->
                               // Log.d("test_test", "data: " + data.weather!![0].date)
                                WeatherStorage.set(data.weather!!)
                                setChartData()
                            },
                            { error ->
                                Log.d("test_test", "Failed to get weather data, error: " + error.toString())
                                showChartError()
                           }
                    )
        }

    }


    fun setupChart() {

        lineChart = chartView
        lineChart!!.setNoDataText("")
        lineChart!!.xAxis.position = XAxis.XAxisPosition.BOTTOM;
        //lineChart!!.xAxis.granularity = 1f
        lineChart!!.xAxis.valueFormatter = object: IAxisValueFormatter {
            private val format = SimpleDateFormat("HH:mm")

            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                var millis = TimeUnit.HOURS.toMillis(value.toLong())
                return format.format( Date(millis))
            }

        }
        lineChart!!.axisRight.isEnabled = false
        lineChart!!.axisLeft.valueFormatter = object: IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                return String.format("%.1f", value) + " m"
            }
        }

        lineChart!!.description.isEnabled = false;
        lineChart!!.legend.isEnabled = false
    }


    fun showChartError() {
        Log.d("test_test", "show chart error: ")
        linear3.visibility = View.GONE
        lineChart!!.setData(null)
        lineChart!!.setNoDataText("Failed to get tides data")
        lineChart!!.setNoDataTextColor(ContextCompat.getColor(this, R.color.red))
        val p = lineChart!!.getPaint(Chart.PAINT_INFO);
        p.textSize = 50f
        lineChart!!.invalidate()

    }


    fun setChartData() {
        if (lineChart == null) {
            return
        }

        for (weather in WeatherStorage.get()!!)     {
            if (weather.date!! == SimpleDateFormat("yyyy-MM-dd").format(selectedDate)) {
                //Log.d("test_test", "date: " + weather.date!!)

                textViewHighTides.text = ""
                textViewLowTides.text = ""
                val entries = ArrayList<Entry>()
                for (tide_data in weather!!.tides!!.tide_data!!) {
                    val entry =  Entry()
                    val d = SimpleDateFormat("yyyy-MM-dd HH:mm").parse(tide_data.tideDateTime)
                    val s = SimpleDateFormat("HH:mm").format(d)
                    entry.x = s.replace(":",".").toFloat()

                   // val ft = (3.28084 * tide_data.tideHeight_mt!!).toFloat()
                    val meters = tide_data.tideHeight_mt!!.toFloat()
                    entry.y = meters
                    entries.add(entry)

                    if (tide_data.tide_type == "HIGH") {
                        val sh = textViewHighTides.text.toString() + "%.2f".format(meters) + " m at " + s + "\n"
                        textViewHighTides.text = sh
                    } else if (tide_data.tide_type == "LOW") {
                        val sl = textViewLowTides.text.toString() + "%.2f".format(meters) + " m at " + s + "\n"
                        textViewLowTides.text = sl
                    }
                }

                val dataSet = LineDataSet(entries, "Label") // add entries to dataset
                dataSet.setColor(ContextCompat.getColor(this, R.color.tides_chart_line_color))
                dataSet.setValueTextColor(Color.BLACK)
                dataSet.fillColor = Color.RED
                dataSet.lineWidth = 2.0f
                dataSet.setDrawFilled(true)
                dataSet.setDrawValues(false)
                dataSet.fillColor = ContextCompat.getColor(this, R.color.tides_chart_fill_color)


                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER)
                val lineData = LineData(dataSet)
                lineChart!!.setData(lineData)
                lineChart!!.invalidate()

                linear3.visibility = View.VISIBLE

               // textViewHighTides.text = "15.76 ft at 01:00\n15.54 ft at 14:00"
               // textViewLowTides.text ="-2.16 ft at 07:50\n-1.75 ft at 20:10"

                return
            }
        }

        showChartError()
    }


}
