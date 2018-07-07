package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_tackle.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.TackleFragment

class TackleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tackle)

        (fragmentTackle as TackleFragment).offlineUrl = "file:///android_asset/pages/tackle/tackleHtm.htm"
        (fragmentTackle as TackleFragment).onlineUrl = "http://bit.ly/2KNOMu6"
        (fragmentTackle as TackleFragment).loadOfflineContent()
    }
}
