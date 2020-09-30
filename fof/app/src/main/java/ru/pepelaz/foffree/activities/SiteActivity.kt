package ru.pepelaz.foffree.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_site.*
import ru.pepelaz.foffree.R

class SiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_site)

        webView.settings.javaScriptEnabled = true
        webView.settings.setSupportZoom(true)
        webView.settings.builtInZoomControls = true;
        webView.webViewClient = object : WebViewClient() {
           // override fun onPageFinished(view: WebView, url: String) {
                //imageViewBackground.visibility = View.GONE
           //     webView.visibility = View.VISIBLE
           // }
        }
        webView.loadUrl("http://www.fish-o-fax.com/")
      //  webView.loadUrl("http://www.coarsefishingfacts.com/index.htm")
    }
}
