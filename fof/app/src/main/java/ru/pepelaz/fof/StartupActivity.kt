package ru.pepelaz.fof

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Handler


class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)

        Handler().postDelayed({
           startActivity(Intent(applicationContext, MainActivity::class.java))
            finish()
        }, 2000)
    }
}
