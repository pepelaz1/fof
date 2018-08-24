package ru.pepelaz.fof.fragments


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_info.view.*

import ru.pepelaz.fof.R
import android.content.Intent
import android.graphics.Color
import android.hardware.SensorManager
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_knot.*
import kotlinx.android.synthetic.main.fragment_info.*
import java.util.*
import android.opengl.ETC1.getWidth
import android.view.WindowManager
import android.view.Display





/**
 * A simple [Fragment] subclass.
 *
 */
class InfoFragment : Fragment() {

    var contentUrl: String? = ""
    var moreUrl: String? = ""
    var imageUrl: String? = ""
    var pd: ProgressDialog? = null
    var listener: IInfoFragment? = null

    interface IInfoFragment {
        fun onPrev()
        fun onNext()

    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_info, container, false)
        v.buttonBack.setOnClickListener({
            activity!!.finish()
        })
        v.buttonMore.setOnClickListener({
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(moreUrl))
            startActivity(browserIntent)
        })

        pd = ProgressDialog.show(context, "", "Loading...", true);
        v.webView.settings.domStorageEnabled = true
        v.webView.settings.javaScriptEnabled = true
        v.webView.settings.setSupportZoom(true)
        v.webView.settings.builtInZoomControls = false
        v.webView.settings.userAgentString = "Mozilla/5.0 (iPhone; CPU iPhone OS 8_3 like Mac OS X) AppleWebKit/600.14 (KHTML, like Gecko) Mobile/12F70"
        var scale = resources.getDimension(R.dimen.species_page_scale)
        v.webView.setInitialScale(scale.toInt())
        v.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (pd != null && pd!!.isShowing) {
                    pd!!.dismiss()
                }
            }

            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                if (url.startsWith("http://back")) {
                    val tel = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    activity!!.finish()
                    return true
                } else {
                    return super.shouldOverrideUrlLoading(view, url)
                }
            }
        }
        v.webView.loadUrl(contentUrl)
        v.buttonPrev.setOnClickListener({
            listener!!.onPrev()
        })

        v.buttonNext.setOnClickListener({
            listener!!.onNext()
        })

        v.buttonMore.setOnClickListener({
            pd = ProgressDialog.show(context, "", "Loading...", true);
            v.webView.loadUrl(moreUrl)
        })

        v.buttonImage.setOnClickListener({
            pd = ProgressDialog.show(context, "", "Loading...", true);
            v.webView.loadUrl(imageUrl)
        })

        return v
    }



    fun updateContent() {
        view!!.webView.loadUrl(contentUrl)
    }


    fun onMid() {
        buttonNext.isEnabled = true
        buttonNext.setTextColor(Color.WHITE)
        buttonPrev.isEnabled = true
        buttonPrev.setTextColor(Color.WHITE)
    }

    fun onLast() {
        buttonNext.isEnabled = false
        buttonNext.setTextColor(Color.GRAY)
    }

    fun onFirst() {
        buttonPrev.isEnabled = false
        buttonPrev.setTextColor(Color.GRAY)
    }


    override fun onDestroy() {
        super.onDestroy()
        if (pd != null && pd!!.isShowing) {
            pd!!.dismiss()
        }
    }

}
