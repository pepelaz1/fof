package ru.pepelaz.foffree.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cooking.*

import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.fragments.PageFragment

class CookingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cooking)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/cookingNew/cookingNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2KMLPKF"
        (fragmentPage as PageFragment).loadOfflineContent()
    }


}
