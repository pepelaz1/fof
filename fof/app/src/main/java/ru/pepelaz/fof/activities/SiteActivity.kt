package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_site.*
import ru.pepelaz.fof.R

class SiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true;
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                imageViewBackground.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        }
        //webView.loadUrl("http://www.fish-o-fax.com/")
        webView.loadUrl("http://www.coarsefishingfacts.com/index.htm")
    }
}
