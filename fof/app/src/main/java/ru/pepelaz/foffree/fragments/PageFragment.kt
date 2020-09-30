package ru.pepelaz.foffree.fragments


import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import ru.pepelaz.foffree.R
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.fragment_page.view.*
import androidx.fragment.app.Fragment


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
