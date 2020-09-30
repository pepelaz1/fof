package ru.pepelaz.foffree.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import ru.pepelaz.foffree.R


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
