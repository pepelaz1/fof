package ru.pepelaz.foffree.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_seasons.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.fragments.PageFragment

class SeasonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seasons)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/seasonsNew/seasonsNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2LpudFt"
        (fragmentPage as PageFragment).loadOfflineContent()
    }

}
