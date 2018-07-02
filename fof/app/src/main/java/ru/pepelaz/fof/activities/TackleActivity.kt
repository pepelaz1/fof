package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_tackle.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.PageFragment

class TackleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tackle)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/tackle/tackle.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2KNOMu6"
        (fragmentPage as PageFragment).loadOfflineContent()
    }
}
