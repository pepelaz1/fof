package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_cooking.*

import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.PageFragment

class CookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/cookingNew/cookingNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2KMLPKF"
        (fragmentPage as PageFragment).loadOfflineContent()
    }


}
