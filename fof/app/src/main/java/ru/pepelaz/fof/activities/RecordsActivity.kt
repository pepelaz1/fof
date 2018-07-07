package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_records.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.R.id.fragmentPage
import ru.pepelaz.fof.fragments.PageFragment

class RecordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/recordsNew/recordsNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2kgNQ6r"
        (fragmentPage as PageFragment).loadOfflineContent()
    }
}
