package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_bait.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.PageFragment

class BaitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bait)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/bait/bait.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2GEn0Om"
        (fragmentPage as PageFragment).loadOfflineContent()
    }

}
