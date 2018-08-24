package ru.pepelaz.fof.fragments


import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

import ru.pepelaz.fof.R
import android.content.Intent
import android.graphics.Color
import android.hardware.SensorManager
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import java.util.*
import android.opengl.ETC1.getWidth
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import kotlinx.android.synthetic.main.fragment_tackle.view.*
import android.support.v4.content.ContextCompat.startActivity
import android.os.Build
import android.annotation.TargetApi
import android.os.Build.VERSION_CODES.N
import android.util.Log
import android.view.*
import kotlin.collections.HashMap


class TackleFragment : Fragment() {

    val offlineUrl: String? = "file:///android_asset/pages/tackle/tackleHtm.htm"
    var onlineUrl: String? = ""
    val origOnlineUrl: String? = "http://bit.ly/2KNOMu6"
    val tacklePages = HashMap<String, Pair<String, String>>()

    var pd: ProgressDialog? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val v = inflater.inflate(R.layout.fragment_tackle, container, false)
        onlineUrl = origOnlineUrl
        loadTacklePages()

        v.buttonHome.setOnClickListener({
            if (v.webView.url == offlineUrl)
                activity!!.finish()
            else
                v.webView.goBack()
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


        v.webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (pd != null && pd!!.isShowing) {
                    pd!!.dismiss()
                }
            }


            @Suppress("OverridingDeprecatedMember")
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                  if (url.startsWith("tel:")) {
                    val tel = Intent(Intent.ACTION_DIAL, Uri.parse(url))
                    startActivity(tel)
                    return true
                } else if (url.contains("mailto:")) {
                    view!!.getContext().startActivity(
                            Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    return true

                } else {
                    return super.shouldOverrideUrlLoading(view, url)
                }
            }


        }

        v.webView.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_UP) {
                val hr = (view as WebView).hitTestResult
                if (hr.extra != null) {
                    Log.d("test_test", "getExtra: " + hr.extra + ", type: " + hr.type)

                    for (e in tacklePages) {

                        if (hr.extra.contains(e.key)) {
                            view!!.webView.loadUrl("file:///android_asset/pages/" + e.value.first);
                            onlineUrl = e.value.second
                        }
                    }
                }
            }
            false
        }

        v.webView.loadUrl(offlineUrl)
        return v
    }



    fun loadTacklePages() {
        tacklePages.put("22870", Pair("boom/boom.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22868", Pair("float/float.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22869", Pair("hook/hook.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22871", Pair("line/line.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22859", Pair("lure/lureApp.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22875", Pair("reel/reel.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22874", Pair("rod/rod.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22873", Pair("swivel/swivelHtm.htm","http://bit.ly/2KNOMu6"))
        tacklePages.put("22867", Pair("weight/weight.htm","http://bit.ly/2KNOMu6"))

    }

    override fun onDestroy() {
        super.onDestroy()
        if (pd != null && pd!!.isShowing) {
            pd!!.dismiss()
        }
    }

}