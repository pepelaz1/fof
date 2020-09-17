package ru.pepelaz.fof.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_knot.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.PageFragment

class KnotActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knot)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/knots2/knots2.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2klR0WC"
        (fragmentPage as PageFragment).loadOfflineContent()
    }
}
