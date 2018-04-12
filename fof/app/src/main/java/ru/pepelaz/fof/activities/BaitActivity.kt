package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import ru.pepelaz.fof.R

class BaitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bait)
    }

    fun buttonCloseClick(v: View) {
        finish()
    }
}
