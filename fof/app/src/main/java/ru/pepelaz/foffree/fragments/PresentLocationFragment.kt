package ru.pepelaz.foffree.fragments

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_present_location.view.*

import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.helpers.CurrentCoords
import ru.pepelaz.foffree.helpers.Utils

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class PresentLocationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var layoutView: View? = null

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
        layoutView = inflater.inflate(R.layout.fragment_present_location, container, false)
        onNewCoords()
        return layoutView
    }



    fun onNewCoords() {
        //  CurrentCoords.latitude = 36.539296
        //  CurrentCoords.longitude = -4.6226728

        // CurrentCoords.latitude = 53.57
        // CurrentCoords.longitude = -2.94

        //CurrentCoords.latitude = -1.0
        //CurrentCoords.longitude = -1.0
       // Log.d("test_test", "onNewCoords, lat: " + CurrentCoords.latitude + ", long: " + CurrentCoords.longitude)
        val geocoder = Geocoder(context)
        var addresses = geocoder.getFromLocation(
                CurrentCoords.latitude,
                CurrentCoords.longitude,
                1)
       // Log.d("test_test", "adresses: " + addresses)
        if (addresses != null && addresses.size > 0 && layoutView!!.textPresentLocation != null) {
            layoutView!!.textPresentLocation.text =  Utils.constructLocationName(addresses[0])
        }


    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PresentLocationFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                PresentLocationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
