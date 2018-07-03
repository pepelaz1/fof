package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_glossary.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.R.id.fragmentPage
import ru.pepelaz.fof.fragments.PageFragment

class GlossaryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_glossary)

        (fragmentPage as PageFragment).offlineUrl = "file:///android_asset/pages/glossaryNew/glossaryNew.htm"
        (fragmentPage as PageFragment).onlineUrl = "http://bit.ly/2KNfjrG"
        (fragmentPage as PageFragment).loadOfflineContent()
    }


}
