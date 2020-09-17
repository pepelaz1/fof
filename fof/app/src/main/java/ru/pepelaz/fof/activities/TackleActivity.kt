package ru.pepelaz.fof.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_tackle.*
import ru.pepelaz.fof.R
import ru.pepelaz.fof.fragments.TackleFragment

class TackleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tackle)
    }
}
