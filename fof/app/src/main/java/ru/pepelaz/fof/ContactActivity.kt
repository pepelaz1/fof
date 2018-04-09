package ru.pepelaz.fof

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class ContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
    }

    fun buttonCloseClick(v: View) {
        finish()
    }
}
