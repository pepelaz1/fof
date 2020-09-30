package ru.pepelaz.foffree.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_about.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.fragments.PageFragment

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/about/about.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2s3NweQ"
        (fragmentPage as PageFragment).loadOfflineContent()
    }
}
