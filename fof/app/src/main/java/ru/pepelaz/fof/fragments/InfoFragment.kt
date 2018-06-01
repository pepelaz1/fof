package ru.pepelaz.fof.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.view.*

import ru.pepelaz.fof.R
import android.content.Intent
import android.net.Uri


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class InfoFragment : Fragment() {

    var moreUrl: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_info, container, false)
        v.buttonHome.setOnClickListener({
            activity!!.finish()
        })
        v.buttonMore.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(moreUrl))
            startActivity(browserIntent)
        })
        return v
    }
}
