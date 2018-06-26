package ru.pepelaz.fof.activities

import android.app.ProgressDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_places.*
import ru.pepelaz.fof.R


class PlacesActivity : AppCompatActivity() {

    var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        pd = ProgressDialog.show(this, "", "Loading...",true);
        webView.settings.domStorageEnabled = true
        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                if (pd != null && pd!!.isShowing) {
                    pd!!.dismiss()
                }
            }
        }

        val contentUrl = "file:///android_asset/pages/places/places.htm"
        webView.loadUrl(contentUrl);
    }


    override fun onDestroy() {
        super.onDestroy()
        if (pd != null && pd!!.isShowing) {
            pd!!.dismiss()
        }
    }

    fun onHomeClick(v: View) {
        finish()
    }

    fun onUpdatesClick(v: View) {
        pd = ProgressDialog.show(this, "", "Loading...",true);
        webView.loadUrl("http://bit.ly/2xwTLxQ")
    }
}
