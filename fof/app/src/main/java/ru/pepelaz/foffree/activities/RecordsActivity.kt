package ru.pepelaz.foffree.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_records.*
import ru.pepelaz.foffree.R
import ru.pepelaz.foffree.fragments.PageFragment

class RecordsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_records)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/recordsNew/recordsNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2kgNQ6r"
        (fragmentPage as PageFragment).loadOfflineContent()
    }
}
