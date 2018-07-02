package ru.pepelaz.fof.fragments


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.pepelaz.fof.R
import android.content.Intent
import android.graphics.Color
import android.hardware.SensorManager
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.*
import android.opengl.ETC1.getWidth
import android.view.WindowManager
import android.view.Display
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.fragment_page.view.*


class PageFragment : Fragment() {

    var offlineUrl: String? = ""
    var onlineUrl: String? = ""
    var pd: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_page, container, false)
        v.buttonHome.setOnClickListener({
            activity!!.finish()
        })
        v.buttonUpdates.setOnClickListener({
            pd = ProgressDialog.show(context, "", "Loading...", true);
            v.webView.loadUrl(onlineUrl)
        })

        pd = ProgressDialog.show(context, "", "Loading...", true);
        v.webView.settings.domStorageEnabled = true
        v.webView.settings.javaScriptEnabled = true
        v.webView.settings.setSupportZoom(true)
        v.webView.settings.builtInZoomControls = false

        //var scale = 60
        //v.webView.setInitialScale(scale)
        v.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (pd != null && pd!!.isShowing) {
                    pd!!.dismiss()
                }
            }
        }
        return v
    }

    fun loadOfflineContent() {
        view!!.webView.loadUrl(offlineUrl)

    }

    override fun onDestroy() {
        super.onDestroy()
        if (pd != null && pd!!.isShowing) {
            pd!!.dismiss()
        }
    }

}
