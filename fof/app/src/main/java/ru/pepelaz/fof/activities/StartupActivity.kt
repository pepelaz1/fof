package ru.pepelaz.fof.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import ru.pepelaz.fof.R


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
