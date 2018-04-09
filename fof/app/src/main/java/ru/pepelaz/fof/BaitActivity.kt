package ru.pepelaz.fof

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class BaitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bait)
    }

    fun buttonCloseClick(v: View) {
        finish()
    }
}
