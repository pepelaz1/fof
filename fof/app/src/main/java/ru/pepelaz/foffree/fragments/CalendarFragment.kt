package ru.pepelaz.foffree.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

import ru.pepelaz.foffree.R
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [TidesCalendarFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [TidesCalendarFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class CalendarFragment : Fragment(), View.OnClickListener {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: ICalendarFragment? = null


    private data class WeekDay(val tag: String, val date: Date, val layout: LinearLayout,
                               var tvDate: TextView, var tvDay: TextView, var selected: Boolean)
    private var weekDays = ArrayList<WeekDay>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var v = inflater.inflate(R.layout.fragment_tides_calendar, container, false)
        val date = Date()
        val sfDayNum = java.text.SimpleDateFormat("dd")
        val sfDayName = java.text.SimpleDateFormat("EEE")
        for (i in 0..6) {
            val tag = "linear" + i.toString()
            var ll = v.findViewWithTag<LinearLayout>("linear" + i.toString())
            ll.setOnClickListener(this)
            val date = addDays(i - 3)
            var tvDate = v.findViewWithTag<TextView>("date" + i.toString())
            tvDate.text = sfDayNum.format(date)
            var tvDay = v.findViewWithTag<TextView>("day" + i.toString())
            tvDay.text = sfDayName.format(date)
            weekDays.add(WeekDay(tag, date, ll, tvDate, tvDay, false))
        }
        weekDays[3].selected = true
        listener!!.onDateSelected(weekDays[3].date)
        update()
        return v
    }

    fun addDays(days: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, days)

        return calendar.time
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ICalendarFragment) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement ICalendarFragment")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    private fun update() {
        for(weekDay in weekDays) {
            if (weekDay.selected) {
                weekDay.tvDate.setTextColor(ContextCompat.getColor(context!!, R.color.tides_calendar_text_selected))
                weekDay.tvDay.setTextColor(ContextCompat.getColor(context!!, R.color.tides_calendar_text_selected))
            } else {
                weekDay.tvDate.setTextColor(ContextCompat.getColor(context!!, R.color.tides_calendar_text_normal))
                weekDay.tvDay.setTextColor(ContextCompat.getColor(context!!, R.color.tides_calendar_text_normal))
            }
        }
    }
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface ICalendarFragment {
        // TODO: Update argument type and name
        fun onDateSelected(date: Date)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TidesCalendarFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                CalendarFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }



    override fun onClick(v: View?) {
        for(weekDay in weekDays) {
            if (weekDay.tag == v!!.tag) {
                weekDay.selected = true
                listener!!.onDateSelected(weekDay.date)
            } else {
                weekDay.selected = false;
            }
        }
        update()
    }
}
