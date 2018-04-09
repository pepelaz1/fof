package ru.pepelaz.fof

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class GetLuckyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_getlucky)
    }

    fun buttonCloseClick(v: View) {
        finish()
    }
}
