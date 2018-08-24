package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_seasons.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.PageFragment

class SeasonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seasons)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/seasonsNew/seasonsNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2LpudFt"
        (fragmentPage as PageFragment).loadOfflineContent()
    }

}
