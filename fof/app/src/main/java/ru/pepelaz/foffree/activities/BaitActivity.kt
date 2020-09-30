package ru.pepelaz.foffree.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_bait.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.fragments.PageFragment

class BaitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bait)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/bait/bait.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2GEn0Om"
        (fragmentPage as PageFragment).loadOfflineContent()
    }

}
